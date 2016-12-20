package io.collect.services.games.howlongtobeat;

/**
 * Utility class
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatUtil {

  /**
   * Utility method used for parsing a given input text (like &quot;44&#189;&quot;) as double (like &quot;44.5&quot;).
   * The input text represents the amount of hours needed to play this game.
   * @param text representing the hours
   * @return the pares time as double
   */
	public static double parseTime(String text) {
		// "65&#189; Hours"; "--" if not known
		if (text.equals("--")) {
			return 0;
		}
		if (text.indexOf("½") > -1) {
			return 0.5 + Double.parseDouble(text.substring(0, text.indexOf("½")));

		}
		return Double.parseDouble(text.substring(0, text.indexOf(" ")));
	}
}
