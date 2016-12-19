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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.junit.Test;

import io.collect.services.games.howlongtobeat.HowLongToBeatDetailPage;
import io.collect.services.games.howlongtobeat.HowLongToBeatEntry;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class TestHowLongToBeatDetailPage {

	@Test
	public void parseDetailBioshock() throws Exception {
		HowLongToBeatDetailPage page = new HowLongToBeatDetailPage(loadDetail("16624"),
				"http://howlongtobeat.com/game.php?id=16624", "16624");
		HowLongToBeatEntry entry = page.getEntry();

		assertNotNull(entry);
		assertEquals("16624", entry.getGameId());
		assertEquals("http://howlongtobeat.com/game.php?id=16624", entry.getDetailLink());
		assertEquals("BioShock Infinite: Burial at Sea - Episode 2 DLC", entry.getName());
		assertEquals(4, entry.getMainStory(), 0);
		assertEquals(5, entry.getMainAndExtra(), 0);
		assertEquals(7, entry.getCompletionist(), 0);
	}
	
	@Test
	public void parseDetailStories() throws Exception {
		HowLongToBeatDetailPage page = new HowLongToBeatDetailPage(loadDetail("35878"),
				"http://howlongtobeat.com/game.php?id=35878", "35878");
		HowLongToBeatEntry entry = page.getEntry();

		assertNotNull(entry);
		assertEquals("35878", entry.getGameId());
		assertEquals("http://howlongtobeat.com/game.php?id=35878", entry.getDetailLink());
		assertEquals("Stories: The Path Of Destinies", entry.getName());
		assertEquals(5.5, entry.getMainStory(), 0);
		assertEquals(7.5, entry.getMainAndExtra(), 0);
		assertEquals(11.5, entry.getCompletionist(), 0);
	}

	private String loadDetail(String gameId) throws UnsupportedEncodingException, IOException {
		return new String(Files.readAllBytes(FileSystems.getDefault()
														.getPath("src/test/resources/howlongtobeat",
																"detail_" + gameId + ".html")),
				"UTF-8");
	}
}
