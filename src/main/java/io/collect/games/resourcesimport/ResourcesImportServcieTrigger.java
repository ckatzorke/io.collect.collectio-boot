package io.collect.games.resourcesimport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public class ResourcesImportServcieTrigger {

	private final ResourcesImportService resourcesImportService;

	@Autowired
	public ResourcesImportServcieTrigger(ResourcesImportService resourcesImportService) {
		this.resourcesImportService = resourcesImportService;
	}

	@Scheduled(initialDelay = 30000, fixedDelay = 2 * 60 * 60 * 1000)
	@Async("giantbombExecutor")
	public void trigger() {
		this.resourcesImportService.startImport();
	}

}
