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
package io.collect.servcies.games.giantbomb.resources.test;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.collect.services.games.giantbomb.config.GiantBombProperties;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Configuration
// @EnableAutoConfiguration
@ComponentScan(basePackages = { "io.collect.giantbomb" })
public class TestConfiguration {
	@Autowired
	GiantBombProperties properties;

	@Bean(name="giantBombRestTemplate")
	RestTemplate createRestTemplate() {
		return new RestTemplate();
	}

	@PostConstruct
	void createProperties() {
		properties.setApikey("e316aff9ff7d945077dabd3a13ecebaad12bb70d");
	}

}
