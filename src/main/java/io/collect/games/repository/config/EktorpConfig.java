package io.collect.games.repository.config;

import java.net.MalformedURLException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Configuration
public class EktorpConfig {

	private StdCouchDbConnector db;

	@Value("${io.collect.couchdb.username}")
	private String user;

	@Value("${io.collect.couchdb.password}")
	private String password;
	
	@Value("${io.collect.couchdb.url}")
	private String url;

	@Bean()
	public CouchDbConnector createConnector() throws MalformedURLException {
		// TODO better httpclient, parameterized
		HttpClient httpClient = new StdHttpClient.Builder()	.username(user)
															.password(password)
															.url(url)
															.build();

		CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
		db = new StdCouchDbConnector("collectio", dbInstance);
		return db;
	}

}
