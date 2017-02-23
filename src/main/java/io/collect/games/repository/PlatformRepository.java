package io.collect.games.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.impl.NameConventions;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.DesignDocument;
import org.ektorp.support.GenerateView;
import org.ektorp.support.StdDesignDocumentFactory;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import io.collect.games.model.Platform;
import javaslang.control.Option;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public final class PlatformRepository extends CouchDbRepositorySupport<Platform> {

	/**
	 * @param type
	 * @param db
	 */
	@Autowired
	public PlatformRepository(CouchDbConnector db) {
		super(Platform.class, db);
		String designDocName = NameConventions.designDocName(Platform.class);
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

	@GenerateView
	public Option<Platform> findByAbbrev(String abbrev) {
		List<Platform> view = queryView("by_abbrev", abbrev);
		if (!CollectionUtils.isEmpty(view)) {
			return Option.of(view.get(0));
		}
		return Option.of((Platform) null);
	}

	@View(name = "by_resourceTypeAndGbId", map = "function(doc) { emit('platform-'+doc.gbId, doc._id); }")
	public Option<Platform> findByGbId(Long gbId) {
		List<Platform> view = queryView("by_resourceTypeAndGbId", "platform-" + String.valueOf(gbId));
		if (!CollectionUtils.isEmpty(view)) {
			return Option.of(view.get(0));
		}
		return Option.of((Platform) null);
	}

}
