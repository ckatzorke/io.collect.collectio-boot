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

	/**
	 * 
	 */
	@Autowired
	public ResourcesImportService(PlatformImporter platformImporter) {
		this.platformImporter = platformImporter;
	}

	public void startImport() {
		importPlatforms();
		// importGames();

	}

	public LocalDateTime getLastImportDateTime() {
		// TODO
		return null;
	}

	private void importPlatforms() {
		this.platformImporter.importPlatforms(null);
	}
}
