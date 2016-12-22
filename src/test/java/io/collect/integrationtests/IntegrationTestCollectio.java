package io.collect.integrationtests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { TestConfiguration.class })
public class IntegrationTestCollectio {

	@LocalServerPort
	int port;

	public void before() {
		RestAssured.port = this.port;
	}

	@Test
	public void hltb() throws Exception {
		given()	.param("game", "Psychonauts")
				.when()
				.get("/rest/howlongtobeat")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("result.resultCount", equalTo(1))
				.and()
				.body("result.entries[0].detailLink",
						equalTo("http://www.howlongtobeat.com/game.php?id=7372"));
	}

	@Test
	public void hltbDetails() throws Exception {
		given()	.param("gameId", "3537")
				.when()
				.get("/rest/howlongtobeatdetail")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("result.gameId", equalTo("3537"))
				.and()
				.body("result.detailLink", equalTo("http://howlongtobeat.com/game.php?id=3537"))
				.and()
				.body("result.imageSource",
						equalTo("http://howlongtobeat.com/gameimages/final-fantasy-15-box-art-280x400.jpg"));
	}

}
