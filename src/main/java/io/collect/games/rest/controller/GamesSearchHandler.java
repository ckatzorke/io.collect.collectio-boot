package io.collect.games.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.collect.games.model.GameIndex;
import io.collect.games.repository.GameIndexRepository;
import io.collect.games.rest.ResultResponseEntity;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RestController
@RequestMapping("/api/games/search")
public class GamesSearchHandler {

	private GameIndexRepository gameIndexRepository;

	@Autowired
	public GamesSearchHandler(GameIndexRepository repo) {
		this.gameIndexRepository = repo;
	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResultResponseEntity<Iterable<GameIndex>> findGames(HttpServletRequest request, @RequestParam String query) {
		ResultResponseEntity<Iterable<GameIndex>> result = new ResultResponseEntity<>(
				gameIndexRepository.findByNameContaining(query.toLowerCase()));
		result.add(new Link(request.getRequestURI() + "?" + request.getQueryString()));
		return result;
	}

}
