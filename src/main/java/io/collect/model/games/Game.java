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
package io.collect.model.games;

import java.util.Date;
import java.util.List;

/**
 * Entity for owned games
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class Game {

	private String id;
	private int collectionNr;
	private String upc;
	private String name;
	private String description;
	private Origin origin;
	private Date purchaseDate;
	private Date updateDate;
	private String studio;
	private String publisher;
	private Platform platform;
	private List<String> genres;
	private boolean played;
	private boolean finished;
	private boolean digital;
	private int rating;
	private String thumbnail;
	private String cover;
	

	private List<String> tags;

	private String giantbombId;
	private String giantbombDetailUrl;
	private String howlongtobeatId;
	private String howlongtobeatDetailUrl;
}
