/*
 * Copyright (C) Christian Katzorke <ckatzorke@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.collect.games.rest;

import io.collect.giantbomb.GiantBombTemplate;
import io.collect.giantbomb.resources.GiantBombGame;
import io.collect.giantbomb.resources.GiantBombMultiResourceResponse;
import io.collect.giantbomb.resources.GiantBombSingleResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestHandler to connect to GiantBomb
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RestController
@RequestMapping("/rest/giantbomb")
public class GiantBombRestHandler {

	/**
	 * 
	 */
	private static final String COLLECTION_GIANTBOMBGAME = "GiantBombGame";
	private GiantBombTemplate gbTemplate;
	private MongoTemplate mongoTemplate;

	@Autowired
	public void setGbTemplate(GiantBombTemplate gbTemplate) {
		this.gbTemplate = gbTemplate;
	}

	@Autowired
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Result search(@RequestParam String query, HttpServletRequest request) {
		Result result = new Result();
		result.add(new Link(request.getRequestURI()));
		GiantBombMultiResourceResponse<GiantBombGame> searchresult = gbTemplate
				.searchForGame(query);
		result.addResultObject("searchresult", searchresult);
		return result;
	}

	@RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
	public Result game(@PathVariable int id, HttpServletRequest request) {
		Result result = new Result();
		result.add(new Link(request.getRequestURI()));
		// 1st look in Mongo, do not use the 3030- prefix!
		GiantBombGame gameDetails = mongoTemplate.findById(id, GiantBombGame.class, COLLECTION_GIANTBOMBGAME);
		if (gameDetails == null) {
			// not found, load from GB and store in Mongo
			GiantBombSingleResourceResponse<GiantBombGame> game = gbTemplate
					.getForGame(String.valueOf(id));
			// check if valid response
			if ("ok".equals(game.error.toLowerCase())) {
				gameDetails = game.results;
				// store in mongo
				mongoTemplate.insert(gameDetails, COLLECTION_GIANTBOMBGAME);
			}
		}
		result.addResultObject("game", gameDetails);
		return result;
	}
}
