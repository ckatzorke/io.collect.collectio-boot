package io.collect.model.games;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity for owned games
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class Game {

	private String uid;
	private int collectionNr;
	private String upc;
	private String name;
	private String deck;
	private String description;
	private Origin origin;
	private LocalDate purchaseDate;
	private LocalDateTime updateDate;
	private String studio;
	private String publisher;
	private String[] platformUids;
	private String[] genres;
	private boolean played;
	private boolean finished;
	private boolean digital;
	private int rating;

	private Set<String> tags;

	private String gbId;
	private String gbThumbUrl;
	private String gbSuperUrl;

	private String hltbId;
	private String hltbDetailUrl;
	private double hltbPlayTimeMain;
	private double hltbPlayTimeCompletionist;
}
