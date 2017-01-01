package io.collect.games.repository;

import org.springframework.data.repository.CrudRepository;

import io.collect.games.model.Platform;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public interface PlatformRepository extends CrudRepository<Platform, Long> {

	Platform findByGbId(Long gbId);
	
	Platform findTopByOrderByGbUpdateDateDesc();

}
