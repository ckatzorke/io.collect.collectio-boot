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
public class HowLongToBeatServiceDefaultImpl implements HowLongToBeatService {

	private static final String HLTB_SEARCH_URL = "http://howlongtobeat.com/search_main.php";
	private static final String HLTB_DETAIL_URL = "http://howlongtobeat.com/game.php";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.collect.services.games.howlongtobeat.IHowLongToBeatService#search(java
	 * .lang.String)
	 */
	@Override
	@Timed
	public HowLongToBeatSearchResultPage search(String gameName) {
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
			return new HowLongToBeatSearchResultPage(gameName, response.getBody());
		} catch (UnirestException e) {
			throw new ContextedRuntimeException("Howlongtobeat not available", e)	.addContextValue("errorId",
					ERROR_HLTB_GONE)
																					.addContextValue("gameName",
																							gameName)
																					.addContextValue("url",
																							HLTB_SEARCH_URL);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.collect.services.games.howlongtobeat.HowLongToBeatService#detail(java.
	 * lang.String)
	 */
	@Override
	public HowLongToBeatEntry detail(String gameId) {
		HttpResponse<String> response;
		try {
			response = Unirest	.get(HLTB_DETAIL_URL)
								.header("accept", "text/html")
								.queryString("id", gameId)
								.asString();
			HowLongToBeatDetailPage detailPage = new HowLongToBeatDetailPage(response.getBody(),
					HLTB_DETAIL_URL + "?id=" + gameId, gameId);
			return detailPage.getEntry();
		} catch (UnirestException e) {
			throw new ContextedRuntimeException("Howlongtobeat not available", e)	.addContextValue("errorId",
					ERROR_HLTB_GONE)
																					.addContextValue("gameId", gameId)
																					.addContextValue("url",
																							HLTB_DETAIL_URL);
		}
	}

}
