package io.collect.games.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * Basic game data, imported from Giantbomb
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Entity
@Data
public class GameIndex {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uid;
	private String name;
	private Long gbId;
	private Date gbAddDate;
	private Date gbUpdateDate;

	@Lob
	private String gbDeck;
	private String gbThumbUrl;
	private String gbSuperUrl;
	@OneToMany
	private List<Platform> platforms;

}
