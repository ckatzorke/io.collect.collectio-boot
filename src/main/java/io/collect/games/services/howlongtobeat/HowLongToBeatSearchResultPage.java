package io.collect.games.services.howlongtobeat;

import static io.collect.games.services.howlongtobeat.HowLongToBeatUtil.calculateSearchHitPropability;
import static io.collect.games.services.howlongtobeat.HowLongToBeatUtil.parseTime;
import static io.collect.games.services.howlongtobeat.HowLongToBeatUtil.parseTypeAndSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Encapsulates the responseBody from an embedded search from
 * <a href="http://howlongtobeat.com">Howlongtobeat</a> (html fragment). If
 * requested, the fragment is analyzed and destructured. See
 * <b>src/test/resources/howlongtobeat/empty|multi|single.html</b> for sample
 * result fragments.
 *
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatSearchResultPage {

	private final String htmlFragment;
	private final String searchTerm;
	private int resultCount = -1;
	private List<HowLongToBeatSearchResultEntry> entries;

	public HowLongToBeatSearchResultPage(String term, String fragment) {
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
	 * @return the searchTerm that was used
	 */
	public String getSearchTerm() {
		return searchTerm;
	}

	/**
	 * @return the resultCount, 0 if no results, 1 if single result >1 for
	 *         multiple hits
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
	public List<HowLongToBeatSearchResultEntry> getEntries() {
		if (entries == null) {
			analyzeFragment();
		}
		return entries;
	}

	private void analyzeFragment() {
		Document html = Jsoup.parseBodyFragment(htmlFragment);
		if (isResult(html)) {
			handleResult(html);
		} else {
			handleNoResult();
		}
	}

	private void handleResult(Document html) {
		Elements liElements = html.getElementsByTag("li");
		this.resultCount = liElements.size();
		Set<HowLongToBeatSearchResultEntry> entrySet = liElements	.stream()
																	.map(this::handleHltbResultLi)
																	.collect(Collectors.toSet());
		this.entries = new ArrayList<>(entrySet);
	}

	private HowLongToBeatSearchResultEntry handleHltbResultLi(Element liElement) {
		Element gameTitle = liElement	.getElementsByTag("a")
										.get(0);
		HowLongToBeatSearchResultEntry entry = new HowLongToBeatSearchResultEntry();
		entry.setName(gameTitle.attr("title"));
		String href = HowLongToBeatService.HLTB_URL + gameTitle.attr("href");
		entry.setDetailLink(href);
		entry.setGameId(href.substring(href.indexOf("?id=") + 4));
		entry.setImageSource(HowLongToBeatService.HLTB_URL + gameTitle	.getElementsByTag("img")
																		.get(0)
																		.attr("src"));
		entry.setPropability(calculateSearchHitPropability(entry.getName(), searchTerm));
		Elements times = liElement.getElementsByClass("search_list_details_block");
		times	.get(0)
				.children()
				.iterator()
				.forEachRemaining((div) -> {
					String type = div	.child(0)
										.text();
					double time = parseTime(div	.child(1)
												.text());
					parseTypeAndSet(entry, type, time);
				});

		return entry;
	}

	private void handleNoResult() {
		this.resultCount = 0;
		this.entries = new ArrayList<>();
	}

	private boolean isResult(Document html) {
		Elements searchResultHeadline = html.getElementsByTag("h3");
		return searchResultHeadline.size() > 0;
	}

}
