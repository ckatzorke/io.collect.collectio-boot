package io.collect.games.resourcesimport;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.collect.games.model.Platform;
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
public class PlatformImporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformImporter.class);
	private static final GiantBombSort SORT_BY_UPDATE_DESC = new GiantBombSort("date_last_updated", false);
	private static final String[] FIELDS = new String[] { "name", "deck", "abbreviation", "id", "image", "date_added",
			"date_last_updated", "site_detail_url", "api_detail_url", "company" };

	private GiantBombTemplate gbTemplate;

	private PlatformRepository platformRepository;

	public PlatformImporter(PlatformRepository platformRepository, GiantBombTemplate gbTemplate) {
		this.platformRepository = platformRepository;
		this.gbTemplate = gbTemplate;
	}

	/**
	 * @param lastImport
	 *            threshold of last import/update, pass <code>null</code> to not
	 *            set any threshold, otherwise only Platform resources that have
	 *            been updated since this passed date/time will be imported
	 */
	public void importPlatforms(LocalDateTime lastImport) {
		try {
			LOGGER.info("Starting import of platforms...");
			LocalDateTime updateThreshold;
			boolean initialImport = false;
			if (lastImport == null) {
				updateThreshold = LocalDateTime.MIN;
				initialImport = true;
				LOGGER.debug("Initial import...");
			} else {
				updateThreshold = lastImport;
				LOGGER.debug("Updating platforms after " + updateThreshold.toString());
			}
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
						LOGGER.debug("Adding platform: " + p);
						if (p.getId() != null) {
							platformRepository.update(p);
						} else {
							platformRepository.add(p);
						}
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
			LOGGER.info("Imported/Updated " + counter + " entries.");
		} catch (Exception e) {
			throw new ImporterException("Error importing/updating platforms!", e);
		} finally {
			LOGGER.info("FINISHED import of platforms...");
		}
	}

	/**
	 * @param platform
	 * @return
	 */
	private Platform convertToPlatform(GiantBombPlatform platform) {
		Platform p = platformRepository	.findByAbbrev(platform.abbreviation)
										.getOrElse(new Platform());
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
		if (platform.company != null) {
			p.setCompany(platform.company.name);
		}
		return p;
	}

}
