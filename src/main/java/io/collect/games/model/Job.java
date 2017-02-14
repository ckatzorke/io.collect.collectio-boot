package io.collect.games.model;

import java.util.Date;

import lombok.Data;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Data
public class Job {

	private JobType jobType;
	private Date started = new Date();
	private Date finished;
	private String info;
	private JobStatus jobStatus = JobStatus.CREATED;

}
