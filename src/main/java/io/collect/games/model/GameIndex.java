package io.collect.games.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Basic game data, imported from Giantbomb
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GameIndex extends GiantBombEntity {

	private List<Integer> platformIds;

}
