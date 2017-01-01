package io.collect.games.repository;

import org.springframework.data.repository.CrudRepository;

import io.collect.games.model.Platform;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public interface PlatformRepository extends CrudRepository<Platform, Long> {

	/**
	 * Looks up the {@link Platform} entry by GiantbombId (not our uid)
	 * 
	 * @param gbId
	 * @return
	 */
	Platform findByGbId(Long gbId);

	/**
	 * Looks up the latest entry of {@link Platform}, ordered by the update date
	 * from Giantbomb. When importing resources from Giantbomb again, only
	 * platform resources that were updated later than this date will be
	 * imported-
	 * 
	 * @return
	 */
	Platform findTopByOrderByGbUpdateDateDesc();

}
