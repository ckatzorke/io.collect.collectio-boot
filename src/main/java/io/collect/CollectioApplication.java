/*
 * Copyright (C) Christian Katzorke <ckatzorke@gmail.com>
 */
package io.collect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = { "io.collect.config", "io.collect.games.rest", "io.collect.games.services",
		"io.collect.games.repository", "io.collect.games.resourcesimport" })
@EnableAsync
@EnableScheduling
public class CollectioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectioApplication.class, args);
	}

}
