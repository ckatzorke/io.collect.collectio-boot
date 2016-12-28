package io.collect.games.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.collect.games.jobs.PlatformImportJob;
import io.collect.games.model.Job;
import io.collect.games.repository.JobRepository;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RestController
@RequestMapping("/api/jobs")
public class JobHandler {

	private JobRepository jobRepository;
	private PlatformImportJob platformImporter;

	@Autowired
	public JobHandler(JobRepository jobRepository, PlatformImportJob platformImporter) {
		this.jobRepository = jobRepository;
		this.platformImporter = platformImporter;
	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public Result<Iterable<Job>> getJobs(HttpServletRequest request) {
		Result<Iterable<Job>> result = new Result<>(jobRepository.findAll());
		result.add(new Link(request.getRequestURI()));
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public Result<Job> createJob(HttpServletRequest request) {
		Job job = platformImporter.importPlatforms();
		Result<Job> result = new Result<>(job);
		result.add(new Link(request.getRequestURI()));
		return result;
	}

}
