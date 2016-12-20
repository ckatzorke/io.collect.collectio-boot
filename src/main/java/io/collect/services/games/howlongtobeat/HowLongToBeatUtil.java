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

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatUtil {
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