package io.collect.games.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

/**
 * Platform entity, as imported from giantbomb.
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Data
@Entity
public class Platform {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uid;

	private String name;
	private String abbrev;

	private Long gbId;
	private Date gbAddDate;
	private Date gbUpdateDate;

	@Lob
	private String gbDeck;
	private String gbThumbUrl;
	private String gbSuperUrl;

}
