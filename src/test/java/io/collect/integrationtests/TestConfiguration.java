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
package io.collect.integrationtests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import io.collect.CollectioApplication;
import io.collect.services.games.giantbomb.config.GiantBombProperties;
import io.collect.services.games.howlongtobeat.HowLongToBeatSearchResultPage;
import io.collect.services.games.howlongtobeat.HowLongToBeatService;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Configuration
@Import(CollectioApplication.class)
public class TestConfiguration {
	@Autowired
	GiantBombProperties properties;

	@Bean(name = "giantBombRestTemplate")
	RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

	@PostConstruct
	void createProperties() {
		properties.setApikey("e316aff9ff7d945077dabd3a13ecebaad12bb70d");
	}

	@Bean(name = "howLongToBeatService")
	HowLongToBeatService createHowLongToBeatService() {
		HowLongToBeatService howLongToBeatService = mock(HowLongToBeatService.class);
		HowLongToBeatSearchResultPage value = new HowLongToBeatSearchResultPage("psychonauts",
				"<h3 class='head_padding shadow_box back_blue'>We Found 1 Games for \"psychonauts\"</h3> <li class=\"back_white shadow_box\"> <div class=\"search_list_image\">\n<a title=\"Psychonauts\" href=\"game.php?id=7373\">\n<img src=\"gameimages/Psychonautsbox.png\"/>\n</a>\n</div> <div class=\"search_list_details\"> <h3><a class=\"text_green\" title=\"Psychonauts\" href=\"game.php?id=7372\">Psychonauts</a></h3> <div class=\"search_list_details_block\"> <div>\n<div class=\"search_list_tidbit\">Main Story</div>\n<div class=\"search_list_tidbit center time_100\">13 Hours </div>\n</div>\n<div>\n<div class=\"search_list_tidbit\">Main + Extra</div>\n<div class=\"search_list_tidbit center time_100\">16 Hours </div>\n</div>\n<div>\n<div class=\"search_list_tidbit\">Completionist</div>\n<div class=\"search_list_tidbit center time_100\">22&#189; Hours </div>\n</div>\n<div>\n<div class=\"search_list_tidbit\">Combined</div>\n<div class=\"search_list_tidbit center time_100\">15&#189; Hours </div>\n</div> </div>\n</div> <div class=\"clear\"></div> </li> ");
		when(howLongToBeatService.search("psychonauts")).thenReturn(value);
		return howLongToBeatService;
	}

}
