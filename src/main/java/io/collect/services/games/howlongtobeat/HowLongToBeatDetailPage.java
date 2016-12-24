package io.collect.services.games.howlongtobeat;

import static io.collect.services.games.howlongtobeat.HowLongToBeatUtil.parseTime;
import static io.collect.services.games.howlongtobeat.HowLongToBeatUtil.parseTypeAndSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Encapsulates the detailpage. Uses the html used by the webite
 * <a href="http://howlongtobeat.com">Howlongtobeat</a> to represent a single
 * game entry and parses the relevant information.
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatDetailPage {

	private final String html;
	private final HowLongToBeatEntry entry;

	/**
	 * Constructs the object
	 * 
	 * @param html
	 *            the markup from
	 *            <a href="http://howlongtobeat.com">Howlongtobeat</a>
	 * @param detailUrl
	 *            the link to this resource from
	 *            <a href="http://howlongtobeat.com">Howlongtobeat</a>
	 * @param gameId
	 *            the id used by
	 *            <a href="http://howlongtobeat.com">Howlongtobeat</a>
	 */
	public HowLongToBeatDetailPage(String html, String detailUrl, String gameId) {
		this.html = html;
		this.entry = analyzeDetailPage(detailUrl, gameId);
	}

	/**
	 * @return the parsed entry
	 */
	public HowLongToBeatEntry getEntry() {
		return this.entry;
	}

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
						parseTypeAndSet(entry, type, time);
					});
		entry.setImageSource(page	.select(".game_image > img")
									.get(0)
									.attr("src"));
		return entry;
	}

}
