package io.collect.games.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RestController
@RequestMapping("/api/games/search")
public class GamesSearchHandler {

//	private GameIndexRepository gameIndexRepository;
//
//	@Autowired
//	public GamesSearchHandler(GameIndexRepository repo) {
//		this.gameIndexRepository = repo;
//	}
//
//	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
//	public ResultResponseEntity<Iterable<GameIndex>> findGames(HttpServletRequest request, @RequestParam String query) {
//		ResultResponseEntity<Iterable<GameIndex>> result = new ResultResponseEntity<>(
//				gameIndexRepository.findByNameContaining(query.toLowerCase()));
//		result.add(new Link(request.getRequestURI() + "?" + request.getQueryString()));
//		return result;
//	}

}
