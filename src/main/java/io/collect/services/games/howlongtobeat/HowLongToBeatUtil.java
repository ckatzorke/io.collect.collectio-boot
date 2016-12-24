package io.collect.services.games.howlongtobeat;

/**
 * Utility class
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatUtil {

	/**
	 * Utility method used for parsing a given input text (like
	 * &quot;44&#189;&quot;) as double (like &quot;44.5&quot;). The input text
	 * represents the amount of hours needed to play this game.
	 * 
	 * @param text
	 *            representing the hours
	 * @return the pares time as double
	 */
	public static double parseTime(String text) {
		// "65&#189; Hours"; "--" if not known
		if (text.equals("--")) {
			return 0;
		}
		if (text.indexOf(" - ") > -1) {
			return handleRange(text);
		}
		return getTime(text);
	}

	/**
	 * @param text like '5 Hours - 12 Hours' or '2½ Hours - 33½ Hours'
	 * @return
	 */
	private static double handleRange(String text) {
		String[] range = text.split(" - ");
		double d = (getTime(range[0]) + getTime(range[1])) / 2;
		return d;
	}

	/**
	 * @param text, can be '12 Hours' or '5½ Hours'
	 * @return
	 */
	private static double getTime(String text) {
		String time = text.substring(0, text.indexOf(" "));
		if (time.indexOf("½") > -1) {
			return 0.5 + Double.parseDouble(time.substring(0, text.indexOf("½")));

		}
		return Double.parseDouble(time);
	}
}
