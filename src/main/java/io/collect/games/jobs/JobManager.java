package io.collect.games.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public class JobManager {

	private PlatformImportJobProcessor platformJob;
	private GameIndexImportJobProcessor gameIndexJob;

	@Autowired
	public JobManager(PlatformImportJobProcessor platformJob, GameIndexImportJobProcessor gameIndexJob) {
		this.platformJob = platformJob;
		this.gameIndexJob = gameIndexJob;
	}

	@Scheduled(initialDelay = 30000, fixedDelay = 2 * 60 * 60 * 1000)
	@Async("giantbombExecutor")
	public void manageJobs() {
		platformJob.createJob();
		gameIndexJob.createJob();
	}

}
