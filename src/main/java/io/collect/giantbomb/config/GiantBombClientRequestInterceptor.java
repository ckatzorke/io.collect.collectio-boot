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
package io.collect.giantbomb.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * {@link ClientHttpRequestInterceptor} to add default headers like User-Agent
 * to identify against <a href="http://www.giantbomb.com">Giantbomb</a>
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class GiantBombClientRequestInterceptor implements ClientHttpRequestInterceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.http.client.ClientHttpRequestInterceptor#intercept(
	 * org.springframework.http.HttpRequest, byte[],
	 * org.springframework.http.client.ClientHttpRequestExecution)
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpHeaders headers = request.getHeaders();
		headers.add("User-Agent", "giantbomb-api; spring-boot search client by Christian Katzorke");
		return execution.execute(request, body);
	}

}
