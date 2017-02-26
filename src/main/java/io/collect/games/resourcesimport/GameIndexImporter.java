package io.collect.games.resourcesimport;

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
import io.collect.games.model.Platform;
import io.collect.games.repository.GameIndexRepository;
import io.collect.games.repository.PlatformRepository;
import io.collect.games.services.giantbomb.GiantBombRequestOptions;
import io.collect.games.services.giantbomb.GiantBombSort;
import io.collect.games.services.giantbomb.GiantBombTemplate;
import io.collect.games.services.giantbomb.resources.GiantBombGame;
import io.collect.games.services.giantbomb.resources.GiantBombMultiResourceResponse;
import io.collect.games.services.giantbomb.resources.GiantBombPlatform;
import javaslang.control.Option;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public class GameIndexImporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameIndexImporter.class);
	private static final GiantBombSort SORT_BY_UPDATE_DESC = new GiantBombSort("date_last_updated", false);
	private static final String[] FIELDS = new String[] { "name", "deck", "abbreviation", "id", "image", "date_added",
			"date_last_updated", "platforms", "site_detail_url", "api_detail_url" };

	private GiantBombTemplate gbTemplate;

	private PlatformRepository platformRepository;
	private GameIndexRepository gameIndexRepository;

	@Value("#{'${io.collect.import.platforms}'.split(',')}")
	private List<String> platforms2Import;

	/**
	 * 
	 */
	public GameIndexImporter(GameIndexRepository gameIndexRepository, PlatformRepository platformRepository,
			GiantBombTemplate gbTemplate) {
		this.gameIndexRepository = gameIndexRepository;
		this.platformRepository = platformRepository;
		this.gbTemplate = gbTemplate;
	}

	public void importGames() {
		try {
			LOGGER.info("Starting import of games for designated platforms");
			List<Platform> platforms4Import = findPlatforms4Import();
			for (Platform p : platforms4Import) {
				importGamesForPlatform(p);
			}
			LOGGER.info("Imported/Updated " + platforms4Import.size() + " platforms ");
		} catch (Exception e) {
			LOGGER.error("Exception occured during importing/updating games!", e);
		} finally {
			LOGGER.info("FINISHED import of games...");
		}
	}

	/**
	 * @param job
	 * @param p
	 */
	private void importGamesForPlatform(Platform p) {
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
					if (gameIdx.getId() == null) {
						gameIndexRepository.add(gameIdx);
					} else {
						gameIndexRepository.update(gameIdx);
					}
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
		p.setLastGamesImport(new Date());
		platformRepository.update(p);
	}

	/**
	 * @return
	 */
	private List<Platform> findPlatforms4Import() {
		List<Platform> importGames = platformRepository.findPlatforms4ImportGames();
		if (CollectionUtils.isEmpty(importGames)) {
			importGames = new ArrayList<>();
			LOGGER.info("Initial import, no platform marked for import yet, marking initial {}", platforms2Import);
			for (String gbId : platforms2Import) {
				Option<Platform> p = platformRepository.findByGbId(Long.parseLong(gbId));
				if (p.isDefined()) {
					Platform platform = p.get();
					LOGGER.debug("Adding platform {} to  list of platforms4import, and setting import to true...", p);
					platform.setImportGames(true);
					platformRepository.update(platform);
					importGames.add(platform);
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
		Option<GameIndex> idx = gameIndexRepository.findByGbId((long) game.id);
		GameIndex gidx = idx.getOrElse(() -> {
			GameIndex i = new GameIndex();
			i.setGbId((long) game.id);
			i.setAddDate(game.date_added);
			return i;
		});
		gidx.setName(game.name);
		gidx.setDeck(game.deck);
		gidx.setUpdateDate(game.date_last_updated);
		if (game.image != null) {
			gidx.setSuperUrl(game.image.super_url);
			gidx.setThumbUrl(game.image.thumb_url);
		}
		List<Long> plats = new ArrayList<>();
		for (GiantBombPlatform platform : game.platforms) {
			plats.add(platformRepository.findByGbId((long) platform.id)
										.get()
										.getGbId());
		}
		gidx.setPlatformIds(plats);
		gidx.setApiDetailUrl(game.api_detail_url);
		gidx.setSiteDetailUrl(game.site_detail_url);
		return gidx;
	}

}
