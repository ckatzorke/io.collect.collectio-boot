package io.collect.games.jobs;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
import io.collect.games.services.giantbomb.resources.GiantBombPlatform;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public class GameIndexImportJobProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameIndexImportJobProcessor.class);
	private static final GiantBombSort SORT_BY_UPDATE_DESC = new GiantBombSort("date_last_updated", false);
	private static final String[] FIELDS = new String[] { "name", "deck", "abbreviation", "id", "image", "date_added",
			"date_last_updated", "platforms", "site_detail_url", "api_detail_url" };

	private GiantBombTemplate gbTemplate;

	private PlatformRepository platformRepository;
	private GameIndexRepository gameIndexRepository;
	private JobRepository jobRepository;

	@Value("#{'${io.collect.import.platforms}'.split(',')}")
	private List<String> platforms2Import;

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

	/**
	 * Creates and start the job
	 */
	public void createJob() {
		Job job = new Job();
		job.setJobType(JobType.IMPORT_GAMEINDEX);
		job = jobRepository.save(job);
		importGames(job);
	}

	private void importGames(Job job) {
		try {
			LOGGER.info("Starting import of games for designated platforms");
			job.setJobStatus(JobStatus.PROCESSING);
			job = jobRepository.save(job);
			List<Platform> platforms4Import = findPlatforms4Import();
			for (Platform p : platforms4Import) {
				importGamesForPlatform(job, p);
			}
			LOGGER.info("Imported/Updated " + platforms4Import.size() + " platforms ");
			job.setInfo("Imported/Updated " + platforms4Import.size() + " platforms ");
			job.setJobStatus(JobStatus.FINISHED);
		} catch (Exception e) {
			job.setJobStatus(JobStatus.STOPPED);
			job.setInfo(e.getMessage());
			LOGGER.error("Exception occured during importing/updating games!", e);
		} finally {
			job.setFinished(new Date());
			job = jobRepository.save(job);
			LOGGER.info("FINISHED import of games...");
		}
	}

	/**
	 * @param job
	 * @param p
	 */
	private void importGamesForPlatform(Job job, Platform p) {
		LOGGER.info("Starting import of games for platform {}", p);
		LocalDateTime updateThreshold;
		Date lastGameImport = p.getLastGamesImport();
		if (lastGameImport == null) {
			updateThreshold = LocalDateTime.MIN;
			LOGGER.debug("Initial import of games for platform " + p.getName() + "...");
		} else {
			updateThreshold = LocalDateTime.ofInstant(lastGameImport.toInstant(), ZoneId.systemDefault());
			LOGGER.debug("Updating games for platform " + p.getName() + " after " + updateThreshold.toString());
		}
		int gamesCounter = 0;
		int offset = 0;
		int limit = 10;
		boolean updateThresholdReached = false;
		boolean endReached = false;
		while (!updateThresholdReached && !endReached) {
			GiantBombRequestOptions options = new GiantBombRequestOptions(limit, offset, SORT_BY_UPDATE_DESC, null,
					FIELDS);
			LOGGER.debug("Getting all games from www.giantbomb.com with {}", options);
			GiantBombMultiResourceResponse<GiantBombGame> games = gbTemplate.getForGames(options, p	.getGbId()
																									.toString());
			for (GiantBombGame game : games.results) {
				LocalDateTime updated = LocalDateTime.ofInstant(game.date_last_updated.toInstant(),
						ZoneId.systemDefault());
				if (updated.isAfter(updateThreshold)) {
					GameIndex gameIdx = convertToGameIndex(game);
					LOGGER.debug("Adding/Updateing game " + gameIdx.getName());
					gameIndexRepository.save(gameIdx);
					gamesCounter++;
				} else {
					LOGGER.debug("Latest update date reached. All resources for '{}' up to date.", p.getName());
					updateThresholdReached = true;
					break;
				}
			}
			if ((limit + offset) > games.number_of_total_results) {
				LOGGER.debug("Reached last page for '{}', continue with next platform.", p.getName());
				endReached = true;
			}
			offset += limit;
			if (updateThresholdReached || endReached) {
				LOGGER.info("Imported/updated {} games for platform '{}'", gamesCounter, p);
			}
		}
		p.setLastGamesImport(job.getStarted());
		platformRepository.save(p);
	}

	/**
	 * @return
	 */
	private List<Platform> findPlatforms4Import() {
		List<Platform> importGames = platformRepository.findByImportGames(true);
		if (CollectionUtils.isEmpty(importGames)) {
			importGames = new ArrayList<>();
			LOGGER.info("Initial import, no platform marked for import yet, marking initial {}", platforms2Import);
			for (String gbId : platforms2Import) {
				Platform p = platformRepository.findByGbId(Long.parseLong(gbId));
				if (p != null) {
					LOGGER.debug("Adding platform {} to  list of platforms4import, and setting import to true...", p);
					p.setImportGames(true);
					platformRepository.save(p);
					importGames.add(p);
				} else {
					LOGGER.warn("No platform with id {} found, check config...", gbId);
				}
			}
		}
		return importGames;
	}

	/**
	 * @param game
	 * @return
	 */
	private GameIndex convertToGameIndex(GiantBombGame game) {
		// check if existing
		GameIndex gidx = gameIndexRepository.findByGbId((long) game.id);
		if (gidx == null) {
			gidx = new GameIndex();
			gidx.setGbId((long) game.id);
			gidx.setAddDate(game.date_added);
		}
		gidx.setName(game.name);
		gidx.setDeck(game.deck);
		gidx.setUpdateDate(game.date_last_updated);
		if (game.image != null) {
			gidx.setSuperUrl(game.image.super_url);
			gidx.setThumbUrl(game.image.thumb_url);
		}
		List<Platform> plats = new ArrayList<>();
		for (GiantBombPlatform platform : game.platforms) {
			plats.add(platformRepository.findByGbId((long) platform.id));
		}
		gidx.setPlatforms(plats);
		gidx.setApiDetailUrl(game.api_detail_url);
		gidx.setSiteDetailUrl(game.site_detail_url);
		return gidx;
	}

}
