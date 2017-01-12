package io.collect.games.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Data
@Entity
public class GiantBombEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uid;

	private String name;

	private Long gbId;
	private Date gbAddDate;
	private Date gbUpdateDate;

	@Lob
	private String gbDeck;
	@Column(length = 512)
	private String gbThumbUrl;
	@Column(length = 512)
	private String gbSuperUrl;

}
