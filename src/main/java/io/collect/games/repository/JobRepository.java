package io.collect.games.repository;

import org.springframework.data.repository.CrudRepository;

import io.collect.games.model.Job;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public interface JobRepository extends CrudRepository<Job, Long> {

}
