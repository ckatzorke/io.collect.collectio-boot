package io.collect.games.services.giantbomb.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

/**
 * Runtime config for services scoped for Giantbomb
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Configuration
public class GiantBombConfig {

	/**
	 * @return a suitable taskexecutor to be used for long running tasks (like
	 *         platformimport) so that tasks are not executed in parallel, for
	 *         consistency reasons and also to keep the traffic to giantbomb at
	 *         a normal level.
	 */
	@Bean(name = "giantbombExecutor")
	AsyncTaskExecutor createExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setBeanName("giantbombExecutor");
		taskExecutor.setMaxPoolSize(1);
		return taskExecutor;
	}

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