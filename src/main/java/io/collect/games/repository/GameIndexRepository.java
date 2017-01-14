package io.collect.games.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
	
	/**
	 * Like query
	 * @param partial
	 * @return
	 */
	@Query("Select g from GameIndex g where LOWER(g.name) LIKE %:query%")
	Iterable<GameIndex> findByNameContaining(@Param("query") String query);

}
