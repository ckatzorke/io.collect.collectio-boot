/*
 * Copyright (C) Christian Katzorke <ckatzorke@gmail.com>
 */
package io.collect.games.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.collect.games.rest.ResultResponseEntity;
import io.collect.games.services.giantbomb.GiantBombTemplate;
import io.collect.games.services.giantbomb.resources.GiantBombGame;
import io.collect.games.services.giantbomb.resources.GiantBombMultiResourceResponse;
import io.collect.games.services.giantbomb.resources.GiantBombSingleResourceResponse;

/**
 * RestHandler to connect to GiantBomb
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RestController
@RequestMapping("/rest/giantbomb")
public class GiantBombRestHandler {

	private GiantBombTemplate gbTemplate;

	@Autowired
	public void setGbTemplate(GiantBombTemplate gbTemplate) {
		this.gbTemplate = gbTemplate;
	}

	@Timed
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResultResponseEntity<GiantBombMultiResourceResponse<GiantBombGame>> search(@RequestParam String query,
			HttpServletRequest request) {
		ResultResponseEntity<GiantBombMultiResourceResponse<GiantBombGame>> result = new ResultResponseEntity<>(gbTemplate.searchForGame(query));
		result.add(new Link(request.getRequestURI() + "?" + request.getQueryString()));
		return result;
	}

	@RequestMapping(value = "/game/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResultResponseEntity<GiantBombGame> game(@PathVariable int id, HttpServletRequest request) {
		GiantBombGame gameDetails = null;
		GiantBombSingleResourceResponse<GiantBombGame> game = gbTemplate.getForGame(String.valueOf(id));
		// check if valid response
		if ("ok".equals(game.error.toLowerCase())) {
			gameDetails = game.results;
		}
		ResultResponseEntity<GiantBombGame> result = new ResultResponseEntity<>(gameDetails);
		result.add(new Link(request.getRequestURI() + "?" + request.getQueryString()));
		return result;
	}
}
