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
package io.collect.rest.games;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.collect.services.games.howlongtobeat.HowLongToBeatService;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RestController
public class HowLongToBeatHandler {

	private final HowLongToBeatService howLongToBeatService;

	@Autowired
	public HowLongToBeatHandler(HowLongToBeatService howlongtobeatService) {
		this.howLongToBeatService = howlongtobeatService;
	}

	@Timed
	@RequestMapping(value = "/rest/howlongtobeat", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Result howLongToBeat(@RequestParam String game, HttpServletRequest request) {
		Result result = new Result();
		result.add(new Link(request.getRequestURI()));
		result.addResultObject("searchResult", this.howLongToBeatService.search(game));
		return result;
	}
	
}
