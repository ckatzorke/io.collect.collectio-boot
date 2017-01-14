package io.collect.games.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.collect.games.model.Platform;
import io.collect.games.repository.PlatformRepository;
import io.collect.games.rest.ResultResponseEntity;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RestController
@RequestMapping("/api/games/platform")
public class PlatformHandler {

	private PlatformRepository platformRepository;

	@Autowired
	public PlatformHandler(PlatformRepository repo) {
		this.platformRepository = repo;
	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResultResponseEntity<Iterable<Platform>> all(HttpServletRequest request) {
		ResultResponseEntity<Iterable<Platform>> result = new ResultResponseEntity<>(platformRepository.findAll());
		result.add(new Link(request.getRequestURI()));
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })

	public ResultResponseEntity<Iterable<Platform>> search(HttpServletRequest request, @RequestParam String query) {
		ResultResponseEntity<Iterable<Platform>> result = new ResultResponseEntity<>(
				platformRepository.findByNameContaining(query.toLowerCase()));
		result.add(new Link(request.getRequestURI() + "?" + request.getQueryString()));
		return result;
	}

}
