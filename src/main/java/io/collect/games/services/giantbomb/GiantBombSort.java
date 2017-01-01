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
package io.collect.games.services.giantbomb;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class GiantBombSort {
	public String field;
	public boolean asc = true;;

	/**
	 * @param field
	 *            the field's name that should be used for sorting, ascending by
	 *            default
	 */
	public GiantBombSort(String field) {
		this.field = field;
	}

	/**
	 * @param field
	 *            the field's name that should be used for sorting
	 * @param asc
	 *            true for ascending, false for descending
	 */
	public GiantBombSort(String field, boolean asc) {
		this.field = field;
		this.asc = asc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.field + ":" + (asc ? "asc" : "desc");
	}
}