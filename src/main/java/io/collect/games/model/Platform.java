package io.collect.games.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Platform entity, as imported from giantbomb.
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Platform extends GiantBombEntity {

	private String abbrev;

	private boolean importGames;
	private Date lastGamesImport;
}
