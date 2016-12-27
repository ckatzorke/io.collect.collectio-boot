package io.collect.games.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Data
public class Platform {

	private Long uid;

	private String name;
	private String abbrev;
	private LocalDate addDate;
	private LocalDateTime updateDate;

	private String gbId;
	private String gbDeck;
	private String gbThumbUrl;
	private String gbSuperUrl;

}
