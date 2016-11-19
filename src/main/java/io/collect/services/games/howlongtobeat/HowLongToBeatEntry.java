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
public class HowLongToBeatEntry {

	private String name;
	private String detailLink;
	private String imageSource;
	private double mainStory;
	private double mainAndExtra;
	private double completionist;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the detailLink
	 */
	public String getDetailLink() {
		return detailLink;
	}

	/**
	 * @param detailLink
	 *            the detailLink to set
	 */
	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}

	/**
	 * @return the imageSource
	 */
	public String getImageSource() {
		return imageSource;
	}

	/**
	 * @param imageSource
	 *            the imageSource to set
	 */
	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	/**
	 * @return the mainStory
	 */
	public double getMainStory() {
		return mainStory;
	}

	/**
	 * @param mainStory
	 *            the mainStory to set
	 */
	public void setMainStory(double mainStory) {
		this.mainStory = mainStory;
	}

	/**
	 * @return the mainAndExtra
	 */
	public double getMainAndExtra() {
		return mainAndExtra;
	}

	/**
	 * @param mainAndExtra
	 *            the mainAndExtra to set
	 */
	public void setMainAndExtra(double mainAndExtra) {
		this.mainAndExtra = mainAndExtra;
	}

	/**
	 * @return the completionist
	 */
	public double getCompletionist() {
		return completionist;
	}

	/**
	 * @param completionist
	 *            the completionist to set
	 */
	public void setCompletionist(double completionist) {
		this.completionist = completionist;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HowLongToBeatEntry [name=" + name + ", detailLink=" + detailLink + ", imageSource=" + imageSource
				+ ", mainStory=" + mainStory + ", mainAndExtra=" + mainAndExtra + ", completionist=" + completionist
				+ "]";
	}

}
