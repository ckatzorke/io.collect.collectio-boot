package io.collect.games.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.collect.games.model.Job;
import io.collect.games.repository.JobRepository;
import io.collect.games.rest.GamesControllerAdvice;
import io.collect.games.rest.ResultResponseEntity;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RestController
@RequestMapping("/api/games/jobs")
public class JobHandler {

	private JobRepository jobRepository;

	@Autowired
	public JobHandler(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResultResponseEntity<Iterable<Job>> getJobs(HttpServletRequest request) {
		ResultResponseEntity<Iterable<Job>> result = new ResultResponseEntity<>(jobRepository.findAll());
		result.add(new Link(request.getRequestURI()));
		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResultResponseEntity<Job> getJobById(HttpServletRequest request, @PathVariable Long id) {
		Job job = jobRepository.findOne(id);
		if (job == null) {
			ContextedRuntimeException exception = new ContextedRuntimeException("No job with id='" + id + "' found.");
			exception.addContextValue(GamesControllerAdvice.CONTEXT_HTTP_STATUS, HttpStatus.NOT_FOUND);
			throw exception;
		}
		ResultResponseEntity<Job> result = new ResultResponseEntity<>(job);
		result.add(new Link(request.getRequestURI() + "?" + request.getQueryString()));
		return result;
	}

}
