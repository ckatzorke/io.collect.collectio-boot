package io.collect.games.repository;

import org.springframework.data.repository.CrudRepository;

import io.collect.games.model.GameIndex;

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

}
