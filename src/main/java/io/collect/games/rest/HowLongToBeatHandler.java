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

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RestController
public class HowLongToBeatHandler {

	@RequestMapping(value = "/rest/howlongtobeat", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public Object howLongToBeat(@RequestParam String game) {
		HttpResponse<String> response;
		try {
			response = Unirest	.post("http://howlongtobeat.com/search_main.php")
								.header("accept", "*/*")
								.header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
								.queryString("page", "1")
								.field("queryString", game)
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Kaputt";
	}
	
	public static void main(String[] args) {
		HowLongToBeatHandler howLongToBeatHandler = new HowLongToBeatHandler();
		System.out.println(howLongToBeatHandler.howLongToBeat("Castelvania"));
	}

}
