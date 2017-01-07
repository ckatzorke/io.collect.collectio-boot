package io.collect.games.jobs;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.collect.games.model.GameIndex;
import io.collect.games.model.Job;
import io.collect.games.model.JobStatus;
import io.collect.games.model.JobType;
import io.collect.games.model.Platform;
import io.collect.games.repository.GameIndexRepository;
import io.collect.games.repository.JobRepository;
import io.collect.games.repository.PlatformRepository;
import io.collect.games.services.giantbomb.GiantBombRequestOptions;
import io.collect.games.services.giantbomb.GiantBombSort;
import io.collect.games.services.giantbomb.GiantBombTemplate;
import io.collect.games.services.giantbomb.resources.GiantBombGame;
import io.collect.games.services.giantbomb.resources.GiantBombMultiResourceResponse;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public class GameIndexImportJobProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameIndexImportJobProcessor.class);
	private static final GiantBombSort SORT_BY_UPDATE_DESC = new GiantBombSort("date_last_updated", false);
	private static final String[] FIELDS = new String[] { "name", "deck", "abbreviation", "id", "image", "date_added",
			"date_last_updated", "platforms" };

	private GiantBombTemplate gbTemplate;

	private PlatformRepository platformRepository;
	private GameIndexRepository gameIndexRepository;
	private JobRepository jobRepository;

	/**
	 * 
	 */
	public GameIndexImportJobProcessor(JobRepository jobRepository, GameIndexRepository gameIndexRepository,
			PlatformRepository platformRepository, GiantBombTemplate gbTemplate) {
		this.jobRepository = jobRepository;
		this.gameIndexRepository = gameIndexRepository;
		this.platformRepository = platformRepository;
		this.gbTemplate = gbTemplate;
	}

	//TODO get it running @Scheduled(initialDelay = 120000, fixedDelay = 12 * 60 * 60 * 1000)
	@Async("giantbombExecutor")
	public void createJob() {
		Job job = new Job();
		job.setJobType(JobType.IMPORT_GAMEINDEX);
		job = jobRepository.save(job);
		importPlatforms(job);
	}

	@Async("giantbombExecutor")
	public void importPlatforms(Job job) {
		try {
			Long platformId = 139l;
			LOGGER.info("********************************");
			LOGGER.info("Starting import of games for platform=" + platformId + "...");
			LOGGER.info("********************************");
			Platform p = platformRepository.findByGbId(platformId);
			if (p == null) {
				throw new IllegalArgumentException("No platform with id " + platformId + " found");
			}
			GameIndex gameIdxLatestUpdate = gameIndexRepository.findTopContainingPlatformByOrderByGbUpdateDateDesc(p); //gameIndexRepository.findTopContainingPlatformOrderByGbUpdateDateDesc(p);
			LocalDateTime updateThreshold;
			boolean initialImport = false;
			if (gameIdxLatestUpdate == null) {
				updateThreshold = LocalDateTime.MIN;
				initialImport = true;
				LOGGER.info("Initial import for platform " + p.getName() + "...");
			} else {
				updateThreshold = LocalDateTime.ofInstant(gameIdxLatestUpdate	.getGbUpdateDate()
																				.toInstant(),
						ZoneId.systemDefault());
				LOGGER.info("Updating games for platform " + p.getName() + " after " + updateThreshold.toString());
			}
			job.setJobStatus(JobStatus.PROCESSING);
			job = jobRepository.save(job);
			int counter = 0;
			int offset = 0;
			int limit = 10;
			boolean updateThresholdReached = false;
			while (!updateThresholdReached) {
				GiantBombRequestOptions options = new GiantBombRequestOptions(limit, offset, SORT_BY_UPDATE_DESC, null,
						FIELDS);
				LOGGER.info("Getting all platforms from www.giantbomb.com with {}", options);
				GiantBombMultiResourceResponse<GiantBombGame> games = gbTemplate.getForGames(options, p);
				for (GiantBombGame game : games.results) {
					LocalDateTime updated = LocalDateTime.ofInstant(game.date_last_updated.toInstant(),
							ZoneId.systemDefault());
					if (updated.isAfter(updateThreshold)) {
						GameIndex gameIdx = convertToGameIndex(game, p);
						handleUpdate(initialImport, gameIdx, p);
						LOGGER.info("Adding/Updateing game " + gameIdx);
						gameIndexRepository.save(gameIdx);
						counter++;
					} else {
						LOGGER.info("Latest update date reached. All resources up to date.");
						updateThresholdReached = true;
						break;
					}
				}
				if ((limit + offset) > games.number_of_total_results) {
					LOGGER.info("reached last page, break");
					break;
				}
				offset += limit;
			}
			job.setInfo(
					"Imported/Updated " + counter + " entries for platform " + p.getName() + "(" + p.getGbId() + ").");
			job.setJobStatus(JobStatus.FINISHED);
		} catch (Exception e) {
			job.setJobStatus(JobStatus.STOPPED);
			job.setInfo(e.getMessage());
		} finally {
			job.setFinished(new Date());
			job = jobRepository.save(job);
			LOGGER.info("********************************");
			LOGGER.info("FINISHED import of platforms...");
			LOGGER.info("********************************");
		}
	}

	/**
	 * @param initialImport
	 * @param p
	 */
	private void handleUpdate(boolean initialImport, GameIndex gidx, Platform p) {
		if (!initialImport) {
			GameIndex existing = gameIndexRepository.findByGbId(gidx.getGbId());
			if (existing != null) {
				gidx.setGbId(existing.getUid());
				List<Platform> platforms = existing.getPlatforms();
				if (!platforms.contains(p)) {
					platforms.add(p);
				}
				gidx.setPlatforms(platforms);
			}
		}
	}

	/**
	 * @param game
	 * @return
	 */
	private GameIndex convertToGameIndex(GiantBombGame game, Platform p) {
		GameIndex gidx = new GameIndex();

		gidx.setGbId((long) game.id);
		gidx.setName(game.name);
		gidx.setGbDeck(game.deck);
		gidx.setGbAddDate(game.date_added);
		gidx.setGbUpdateDate(game.date_last_updated);
		gidx.setGbSuperUrl(game.image.super_url);
		gidx.setGbThumbUrl(game.image.thumb_url);
		gidx.setPlatforms(Arrays.asList(p));
		return gidx;
	}

}
