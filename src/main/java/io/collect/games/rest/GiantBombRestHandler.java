/*
 * Copyright (C) Christian Katzorke <ckatzorke@gmail.com>
 */
package io.collect.games.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.collect.giantbomb.GiantBombTemplate;
import io.collect.giantbomb.resources.GiantBombGame;
import io.collect.giantbomb.resources.GiantBombMultiResourceResponse;
import io.collect.giantbomb.resources.GiantBombSingleResourceResponse;

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

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public Result search(@RequestParam String query, HttpServletRequest request) {
		Result result = new Result();
		result.add(new Link(request.getRequestURI()));
		GiantBombMultiResourceResponse<GiantBombGame> searchresult = gbTemplate.searchForGame(query);
		result.addResultObject("searchresult", searchresult);
		return result;
	}

	@RequestMapping(value = "/game/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public Result game(@PathVariable int id, HttpServletRequest request) {
		Result result = new Result();
		result.add(new Link(request.getRequestURI()));
		GiantBombGame gameDetails = null;
		GiantBombSingleResourceResponse<GiantBombGame> game = gbTemplate.getForGame(String.valueOf(id));
		// check if valid response
		if ("ok".equals(game.error.toLowerCase())) {
			gameDetails = game.results;
		}
		result.addResultObject("game", gameDetails);
		return result;
	}
}
