package io.collect.games.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Data
@JsonIgnoreProperties({ "id", "revision" })
public class GiantBombEntity {
	@JsonProperty("_id")
	private String id;
	@JsonProperty("_rev")
	private String revision;

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
