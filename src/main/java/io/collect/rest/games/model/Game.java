/*
 * Copyright (C) Christian Katzorke <ckatzorke@gmail.com>
 */
package io.collect.rest.games.model;

import java.util.Date;

/**
 * Model class for collected Games
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class Game {

	// @Id
	String id;

	int collectionnr;
	String name;
	String[] platform;
	boolean played;
	boolean finished;
	boolean digital;
	String[] tags;
	int rating;

	Date bought;
	String placeBought;
	String ean;
	String region;

	Date added;
	Date updated;

	int gbGameId;

}
