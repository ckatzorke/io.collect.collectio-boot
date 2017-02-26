package io.collect.games.resourcesimport;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public class ResourcesImportService {

	private final PlatformImporter platformImporter;
	private final GameIndexImporter gamesImporter;

	@Autowired
	public ResourcesImportService(PlatformImporter platformImporter, GameIndexImporter gamesImporter) {
		this.platformImporter = platformImporter;
		this.gamesImporter = gamesImporter;
	}

	public void startImport() {
		this.platformImporter.importPlatforms(null);
		this.gamesImporter.importGames();

	}

	public LocalDateTime getLastImportDateTime() {
		// TODO
		return null;
	}

}
