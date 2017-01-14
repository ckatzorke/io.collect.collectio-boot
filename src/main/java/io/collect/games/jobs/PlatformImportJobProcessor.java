package io.collect.games.jobs;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.collect.games.model.Job;
import io.collect.games.model.JobStatus;
import io.collect.games.model.JobType;
import io.collect.games.model.Platform;
import io.collect.games.repository.JobRepository;
import io.collect.games.repository.PlatformRepository;
import io.collect.games.services.giantbomb.GiantBombRequestOptions;
import io.collect.games.services.giantbomb.GiantBombSort;
import io.collect.games.services.giantbomb.GiantBombTemplate;
import io.collect.games.services.giantbomb.resources.GiantBombMultiResourceResponse;
import io.collect.games.services.giantbomb.resources.GiantBombPlatform;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public class PlatformImportJobProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformImportJobProcessor.class);
	private static final GiantBombSort SORT_BY_UPDATE_DESC = new GiantBombSort("date_last_updated", false);
	private static final String[] FIELDS = new String[] { "name", "deck", "abbreviation", "id", "image", "date_added",
			"date_last_updated", "site_detail_url", "api_detail_url" };

	private GiantBombTemplate gbTemplate;

	private PlatformRepository platformRepository;
	private JobRepository jobRepository;

	public PlatformImportJobProcessor(JobRepository jobRepository, PlatformRepository platformRepository,
			GiantBombTemplate gbTemplate) {
		this.jobRepository = jobRepository;
		this.platformRepository = platformRepository;
		this.gbTemplate = gbTemplate;
	}

	/**
	 * Creates and starts the importjob
	 */
	public void createJob() {
		Job job = new Job();
		job.setJobType(JobType.IMPORT_PLATFORM);
		job = jobRepository.save(job);
		importPlatforms(job);
	}

	private void importPlatforms(Job job) {
		try {
			LOGGER.info("Starting import of platforms...");
			Platform latestUpdate = platformRepository.findTopByOrderByUpdateDateDesc();
			LocalDateTime updateThreshold;
			boolean initialImport = false;
			if (latestUpdate == null) {
				updateThreshold = LocalDateTime.MIN;
				initialImport = true;
				LOGGER.debug("Initial import...");
			} else {
				updateThreshold = LocalDateTime.ofInstant(latestUpdate	.getUpdateDate()
																		.toInstant(),
						ZoneId.systemDefault());
				LOGGER.debug("Updating platforms after " + updateThreshold.toString());
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
				LOGGER.debug("Getting all platforms from www.giantbomb.com with {}", options);
				GiantBombMultiResourceResponse<GiantBombPlatform> platforms = gbTemplate.getForPlatforms(options);
				for (GiantBombPlatform platform : platforms.results) {
					LocalDateTime updated = LocalDateTime.ofInstant(platform.date_last_updated.toInstant(),
							ZoneId.systemDefault());
					if (updated.isAfter(updateThreshold)) {
						Platform p = convertToPlatform(platform);
						handleUpdate(initialImport, p);
						LOGGER.debug("Adding platform: " + p);
						platformRepository.save(p);
						counter++;
					} else {
						LOGGER.debug("Latest update date reached. All resources up to date.");
						updateThresholdReached = true;
						break;
					}
				}
				if ((limit + offset) > platforms.number_of_total_results) {
					LOGGER.info("reached last page, break");
					break;
				}
				offset += limit;
			}
			job.setInfo("Imported/Updated " + counter + " entries.");
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
	private void handleUpdate(boolean initialImport, Platform p) {
		if (!initialImport) {
			Platform existing = platformRepository.findByGbId(p.getGbId());
			if (existing != null) {
				p.setGbId(existing.getUid());
			}
		}
	}

	/**
	 * @param platform
	 * @return
	 */
	private Platform convertToPlatform(GiantBombPlatform platform) {
		Platform p = new Platform();
		p.setGbId((long) platform.id);
		p.setName(platform.name);
		p.setDeck(platform.deck);
		p.setAbbrev(platform.abbreviation);
		p.setAddDate(platform.date_added);
		p.setUpdateDate(platform.date_last_updated);
		if (platform.image != null) {
			p.setSuperUrl(platform.image.super_url);
			p.setThumbUrl(platform.image.thumb_url);
		}
		p.setApiDetailUrl(platform.api_detail_url);
		p.setSiteDetailUrl(platform.site_detail_url);
		return p;
	}

}
