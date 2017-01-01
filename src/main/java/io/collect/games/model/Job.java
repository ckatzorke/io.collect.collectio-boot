package io.collect.games.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Entity
@Data
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private JobType jobType;
	private Date started = new Date();
	private Date finished;
	private String info;
	private JobStatus jobStatus = JobStatus.CREATED;

}
