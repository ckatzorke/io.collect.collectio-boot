package io.collect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = { "io.collect.games.rest", "io.collect.giantbomb", "io.collect.backend" })
public class CollectioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectioApplication.class, args);
	}
	
	@Configuration
	protected static class Config {
		@Bean
		RestTemplate createResttemplate() {
			// TODO create a "good" RestTemplate with appropriate
			// settings/timeouts/connectionpool.
			return new RestTemplate();
		}
	}

	

}
