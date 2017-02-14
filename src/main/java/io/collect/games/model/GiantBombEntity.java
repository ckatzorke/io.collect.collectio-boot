package io.collect.games.model;

import java.util.Date;

import lombok.Data;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Data
public class GiantBombEntity {

	private String name;

	private Long gbId;
	private Date addDate;
	private Date updateDate;

	private String deck;
	private String thumbUrl;
	private String superUrl;

	private String siteDetailUrl;
	private String apiDetailUrl;

}
