package io.collect.games.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
	Platform findTopByOrderByUpdateDateDesc();

	/**
	 * @return all platforms where the flag that games should be added to
	 *         {@link GameIndexRepository} is set to true
	 */
	List<Platform> findByImportGames(boolean importGames);

	/**
	 * Like search
	 * 
	 * @param query
	 * @return
	 */
	@Query("Select p from Platform p where LOWER(p.name) LIKE %:query%")
	Iterable<Platform> findByNameContaining(@Param("query") String query);

}
