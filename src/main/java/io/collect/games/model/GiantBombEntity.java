package io.collect.games.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Data
@Entity
@Table(indexes = { @Index(columnList = "gbId"), @Index(columnList = "name") })
public class GiantBombEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uid;

	private String name;

	private Long gbId;
	private Date addDate;
	private Date updateDate;

	@Lob
	private String deck;
	@Column(length = 512)
	private String thumbUrl;
	@Column(length = 512)
	private String superUrl;
	
	private String siteDetailUrl;
	private String apiDetailUrl;
	

}
