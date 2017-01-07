package io.collect.games.repository;

import org.springframework.data.repository.CrudRepository;

import io.collect.games.model.GameIndex;
import io.collect.games.model.Platform;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public interface GameIndexRepository extends CrudRepository<GameIndex, Long> {

	/**
	 * Looks up the {@link GameIndex} entry by GiantbombId (not our uid)
	 * 
	 * @param gbId
	 * @return
	 */
	GameIndex findByGbId(Long gbId);

	/**
	 * Looks up the latest entry of {@link GameIndex}, ordered by the update date
	 * from Giantbomb. When importing resources from Giantbomb again, only
	 * games resources that were updated later than this date will be
	 * imported-
	 * 
	 * @return
	 */
	GameIndex findTopContainingPlatformByOrderByGbUpdateDateDesc(Platform p);

}
