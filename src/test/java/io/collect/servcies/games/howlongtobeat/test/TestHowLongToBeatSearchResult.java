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
package io.collect.servcies.games.howlongtobeat.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import io.collect.games.services.howlongtobeat.HowLongToBeatEntry;
import io.collect.games.services.howlongtobeat.HowLongToBeatSearchResultEntry;
import io.collect.games.services.howlongtobeat.HowLongToBeatSearchResultPage;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class TestHowLongToBeatSearchResult {

	@Test
	public void noresults() throws Exception {
		final String searchterm = "witcherr";
		String fragment = loadFragment("empty.html");
		HowLongToBeatSearchResultPage result = new HowLongToBeatSearchResultPage(searchterm, fragment);
		assertEquals(searchterm, result.getSearchTerm());
		assertNotNull(result.getHtmlFragment());
		assertEquals(fragment, result.getHtmlFragment());
		assertEquals(0, result.getResultCount());
		assertTrue(result	.getEntries()
							.isEmpty());
	}

	@Test
	public void multiresults() throws Exception {
		final String searchterm = "witcher 3";
		String fragment = loadFragment("multi.html");
		HowLongToBeatSearchResultPage result = new HowLongToBeatSearchResultPage(searchterm, fragment);
		assertEquals(searchterm, result.getSearchTerm());
		assertNotNull(result.getHtmlFragment());
		assertEquals(fragment, result.getHtmlFragment());
		assertEquals(4, result.getResultCount());
		assertEquals(4, result	.getEntries()
								.size());
		Set<String> names = result	.getEntries()
									.stream()
									.map(entry -> entry.getName())
									.collect(Collectors.toSet());
		assertEquals(4, names.size());
		assertTrue(names.contains("The Witcher 3: Wild Hunt"));
		assertTrue(names.contains("The Witcher 3: Wild Hunt - Blood and Wine"));
		assertTrue(names.contains("The Witcher 3: Wild Hunt - Hearts of Stone"));
		assertTrue(names.contains("Witcher 3: Wild Hunt - Game of the Year Edition"));

		HowLongToBeatEntry entry = result	.getEntries()
											.stream()
											.filter(e -> e	.getName()
															.equals("The Witcher 3: Wild Hunt"))
											.findFirst()
											.get();

		assertEquals(46.5, entry.getMainStory(), 0);
		assertEquals(99.5, entry.getMainAndExtra(), 0);
		assertEquals(165, entry.getCompletionist(), 0);

	}

	@Test
	public void singleresult() throws Exception {
		final String searchterm = "Witcher 3: Wild Hunt - game of the Year Edition";
		String fragment = loadFragment("single.html");
		HowLongToBeatSearchResultPage result = new HowLongToBeatSearchResultPage(searchterm, fragment);
		assertEquals(1, result.getResultCount());
		assertEquals(1, result	.getEntries()
								.size());

		HowLongToBeatSearchResultEntry entry = result	.getEntries()
														.stream()
														.findFirst()
														.get();

		assertEquals(55.5, entry.getMainStory(), 0);
		assertEquals(0, entry.getCompletionist(), 0);
		assertEquals(1.0, entry.getPropability(), 0);

		assertEquals(
				"http://www.howlongtobeat.com/gameimages/1472548261_main_The_Witcher_3_Game_of_the_Year_Edition.jpg",
				entry.getImageSource());
		assertEquals("http://www.howlongtobeat.com/game.php?id=40171", entry.getDetailLink());
		assertEquals("40171", entry.getGameId());

	}

	@Test
	public void searchOverwatch() throws Exception {
		final String searchterm = "overwatch";
		String fragment = loadFragment("search_overwatch.html");
		HowLongToBeatSearchResultPage result = new HowLongToBeatSearchResultPage(searchterm, fragment);
		assertEquals(1, result.getResultCount());
		assertEquals(1, result	.getEntries()
								.size());

		HowLongToBeatEntry entry = result	.getEntries()
											.stream()
											.findFirst()
											.get();
		// only Vs.
		assertEquals(0, entry.getMainStory(), 0);
		assertEquals(0, entry.getCompletionist(), 0);
		assertEquals(49.0, entry.getVs(), 0);

		assertEquals("http://www.howlongtobeat.com/gameimages/31590_Overwatch.jpg", entry.getImageSource());
		assertEquals("http://www.howlongtobeat.com/game.php?id=31590", entry.getDetailLink());
		assertEquals("31590", entry.getGameId());

	}

	private String loadFragment(String filename) throws UnsupportedEncodingException, IOException {
		return new String(Files.readAllBytes(FileSystems.getDefault()
														.getPath("src/test/resources/howlongtobeat", filename)),
				"UTF-8");
	}

}
