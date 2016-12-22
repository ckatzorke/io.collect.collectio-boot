/*
 * Copyright (C) Christian Katzorke <ckatzorke@gmail.com>
 */
package io.collect.rest.games;

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

import io.collect.services.games.giantbomb.GiantBombTemplate;
import io.collect.services.games.giantbomb.resources.GiantBombGame;
import io.collect.services.games.giantbomb.resources.GiantBombMultiResourceResponse;
import io.collect.services.games.giantbomb.resources.GiantBombSingleResourceResponse;

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
	public Result<GiantBombMultiResourceResponse<GiantBombGame>> search(@RequestParam String query,
			HttpServletRequest request) {
		Result<GiantBombMultiResourceResponse<GiantBombGame>> result = new Result<>(gbTemplate.searchForGame(query));
		result.add(new Link(request.getRequestURI()));
		return result;
	}

	@RequestMapping(value = "/game/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public Result<GiantBombGame> game(@PathVariable int id, HttpServletRequest request) {
		GiantBombGame gameDetails = null;
		GiantBombSingleResourceResponse<GiantBombGame> game = gbTemplate.getForGame(String.valueOf(id));
		// check if valid response
		if ("ok".equals(game.error.toLowerCase())) {
			gameDetails = game.results;
		}
		Result<GiantBombGame> result = new Result<>(gameDetails);
		result.add(new Link(request.getRequestURI()));
		return result;
	}
}
