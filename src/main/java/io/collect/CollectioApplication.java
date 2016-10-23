/*
 * Copyright (C) Christian Katzorke <ckatzorke@gmail.com>
 */
package io.collect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "io.collect.config", "io.collect.games.rest", "io.collect.giantbomb" })
public class CollectioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectioApplication.class, args);
	}

}
