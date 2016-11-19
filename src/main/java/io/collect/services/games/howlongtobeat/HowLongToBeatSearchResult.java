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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Encapsulates the responseBody from Howlongtobeat (html fragment). If
 * requested, the fragment is analyzed and destructured.
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatSearchResult {

	private final String htmlFragment;
	private final String searchTerm;
	private int resultCount = -1;
	private List<HowLongToBeatEntry> entries;

	public HowLongToBeatSearchResult(String term, String fragment) {
		this.searchTerm = term;
		this.htmlFragment = fragment;
	}

	/**
	 * @return the htmlFragment
	 */
	public String getHtmlFragment() {
		return htmlFragment;
	}

	/**
	 * @return the searchTerm
	 */
	public String getSearchTerm() {
		return searchTerm;
	}

	/**
	 * @return the resultCount
	 */
	public int getResultCount() {
		if (resultCount == -1) {
			analyzeFragment();
		}
		return resultCount;
	}

	/**
	 * @return the entries
	 */
	public List<HowLongToBeatEntry> getEntries() {
		if (entries == null) {
			analyzeFragment();
		}
		return entries;
	}

	private void analyzeFragment() {
		Document html = Jsoup.parseBodyFragment(htmlFragment);
		Elements searchResultHeadline = html.getElementsByTag("h3");
		if (searchResultHeadline.isEmpty()) {
			this.resultCount = 0;
			this.entries = new ArrayList<>();
		} else {
			Elements liElements = html.getElementsByTag("li");
			this.resultCount = liElements.size();
			Set<HowLongToBeatEntry> entrySet = liElements	.stream()
															.map(liElement -> {
																Element anchor = liElement	.getElementsByTag("a")
																							.get(0);
																HowLongToBeatEntry entry = new HowLongToBeatEntry();
																entry.setName(anchor.attr("title"));
																entry.setDetailLink(HowLongToBeatService.HLTB_URL
																		+ anchor.attr("href"));
																entry.setImageSource(HowLongToBeatService.HLTB_URL
																		+ anchor.getElementsByTag("img")
																				.get(0)
																				.attr("src"));
																Elements times = liElement.getElementsByClass(
																		"center");
																double mainStory = parseTime(times	.get(0)
																						.text());
																double mainAndExtra = parseTime(times	.get(1)
																							.text());
																double completionist = parseTime(times	.get(2)
																							.text());
																entry.setMainStory(mainStory);
																entry.setMainAndExtra(mainAndExtra);
																entry.setCompletionist(completionist);
																return entry;
															})
															.collect(Collectors.toSet());
			this.entries = new ArrayList<>(entrySet);
		}
	}

	private double parseTime(String text) {
		// "65&#189; Hours"; "--" if not known
		if(text.equals("--")){
			return 0;
		}
		if(text.indexOf("½")>-1){
			return 0.5 + Double.parseDouble(text.substring(0,  text.indexOf("½")));
					
		}
		return Double.parseDouble(text.substring(0,  text.indexOf(" ")));
	}

}
