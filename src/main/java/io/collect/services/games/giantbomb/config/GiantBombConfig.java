package io.collect.services.games.giantbomb.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Runtime config for services scoped for Giantbomb
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Configuration
public class GiantBombConfig {
	@Bean(name = "giantBombRestTemplate")
	RestTemplate createResttemplate() {
		// HttpComponentsClientHttpRequestFactory httpRequestFactory = new
		// HttpComponentsClientHttpRequestFactory(
		// HttpClientBuilder.create().setConnectionManager(new
		// PoolingHttpClientConnectionManager()).build());
		// httpRequestFactory.setConnectionRequestTimeout(1000);
		// httpRequestFactory.setConnectTimeout(1000);
		// httpRequestFactory.setReadTimeout(10000);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(Collections.singletonList(new GiantBombClientRequestInterceptor()));
		return restTemplate;
	}
}