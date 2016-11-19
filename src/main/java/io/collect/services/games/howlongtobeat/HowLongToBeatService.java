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
package io.collect.services.games.howlongtobeat;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.Timed;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Serviceencapsulation for calls against
 * <a href="http://howlongtobeat.com/">howlongtobeat.com</a>. Great website,
 * great service.
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Service
public class HowLongToBeatService {

	public static final String ERROR_HLTB_GONE = "HLTB_GONE";
	public static final String HLTB_URL = "http://www.howlongtobeat.com/";
	private static final String HLTB_SEARCH_URL = HLTB_URL + "search_main.php";

	/**
	 * Plain html search. Does not know anything about the result. Can throw a
	 * 
	 * @param gameName
	 * @return
	 */
	@Timed(name = "howlongtobeat#searchAsHtml")
	public String searchAsHtml(String gameName) {
		HttpResponse<String> response;
		try {
			response = Unirest	.post(HLTB_SEARCH_URL)
								.header("accept", "*/*")
								.header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
								.queryString("page", "1")
								.field("queryString", gameName)
								.field("t", "games")
								.field("sorthead", "popular")
								.field("sortd", "Normal Order")
								.field("plat", "")
								.field("length_type", "main")
								.field("length_min", "")
								.field("length_max", "")
								.field("detail", "0")
								.asString();
			return response.getBody();
		} catch (UnirestException e) {
			throw new ContextedRuntimeException("Howlongtobeat not available", e)	.addContextValue("errorId",
					ERROR_HLTB_GONE)
																					.addContextValue("gameName",
																							gameName)
																					.addContextValue("url",
																							HLTB_SEARCH_URL);
		}
	}

}
