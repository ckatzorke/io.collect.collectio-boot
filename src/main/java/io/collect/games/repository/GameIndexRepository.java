package io.collect.games.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.impl.NameConventions;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.DesignDocument;
import org.ektorp.support.StdDesignDocumentFactory;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import io.collect.games.model.GameIndex;
import javaslang.control.Option;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public final class GameIndexRepository extends CouchDbRepositorySupport<GameIndex> {

	/**
	 * @param type
	 * @param db
	 */
	@Autowired
	public GameIndexRepository(CouchDbConnector db) {
		super(GameIndex.class, db);
		String designDocName = NameConventions.designDocName(GameIndex.class);
		StdDesignDocumentFactory ddFactory = new StdDesignDocumentFactory();
		DesignDocument designDocument = ddFactory.generateFrom(this);
		designDocument.setId(designDocName);
		try {
			DesignDocument existing = ddFactory.getFromDatabase(db, designDocName);
			designDocument.setRevision(existing.getRevision());
			db.update(designDocument);
		} catch (DocumentNotFoundException dnf) {
			db.create(designDocument);
		}
	}

	@View(name = "by_resourceTypeAndGbId", map = "function(doc) { if(doc.resource && doc.resource === 'game') { emit(''+doc.gbId, doc._id); } }")
	public Option<GameIndex> findByGbId(Long gbId) {
		List<GameIndex> view = queryView("by_resourceTypeAndGbId", "game-" + String.valueOf(gbId));
		if (!CollectionUtils.isEmpty(view)) {
			return Option.of(view.get(0));
		}
		return Option.of((GameIndex) null);
	}

}
