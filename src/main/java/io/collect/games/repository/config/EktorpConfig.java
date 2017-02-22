package io.collect.games.repository.config;

import java.net.MalformedURLException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Configuration
public class EktorpConfig {

	@Bean()
	public CouchDbConnector createConnector() throws MalformedURLException {
		// TODO better httpclient
		HttpClient httpClient = new StdHttpClient.Builder()	.username("admin").password("admin").url("http://localhost:5984")
															.build();

		CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
		CouchDbConnector db = new StdCouchDbConnector("collectio", dbInstance);
		return db;
	}
}
