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
package io.collect.services.games.howlongtobeat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatDetailPage {

	private final String html;
	private final HowLongToBeatEntry entry;

	public HowLongToBeatDetailPage(String html, String detailUrl, String gameId) {
		this.html = html;
		this.entry = analyzeDetailPage(detailUrl, gameId);
	}

	/**
	 * @param gameId
	 * @param detailUrl
	 * @return
	 */
	private HowLongToBeatEntry analyzeDetailPage(String detailUrl, String gameId) {
		final HowLongToBeatEntry entry = new HowLongToBeatEntry();
		Document page = Jsoup.parse(this.html);
		Elements title = page.getElementsByClass("profile_header");
		entry.setName(title.text());
		entry.setDetailLink(detailUrl);
		entry.setGameId(gameId);
		Elements liElements = page.select(".game_times > li");
		liElements	.stream()
					.forEach(li -> {
						String type = li.getElementsByTag("h5")
										.get(0)
										.text();
						double time = parseTime(li	.getElementsByTag("div")
													.text());
						if ("Main Story".equalsIgnoreCase(type)) {
							entry.setMainStory(time);
						} else if ("Main + Extras".equals(type)) {
							entry.setMainAndExtra(time);
						} else if ("Completionist".equals(type)) {
							entry.setCompletionist(time);
						}
					});
		entry.setImageSource(page	.select(".game_image > img")
									.get(0)
									.attr("src"));
		return entry;
	}

	/**
	 * @param text
	 *            "x hours", "y½ hours"
	 * @return
	 */
	private double parseTime(String text) {
		String timeAsString = text.substring(0, text.indexOf(' '));
		if (timeAsString.indexOf('½') > 0) {
			timeAsString = timeAsString.substring(0, timeAsString.indexOf('½'));
			return Double.parseDouble(timeAsString) + 0.5;
		}
		return Double.parseDouble(timeAsString);
	}

	public HowLongToBeatEntry getEntry() {
		return this.entry;
	}

}
