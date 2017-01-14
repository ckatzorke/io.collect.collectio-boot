package io.collect.games.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Basic game data, imported from Giantbomb
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class GameIndex extends GiantBombEntity {

	@ManyToMany(cascade=CascadeType.REMOVE)
	private List<Platform> platforms;

}
