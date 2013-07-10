package org.geese.ci.classifier.store.posix;

import java.sql.SQLException;

import org.geese.ci.classifier.store.ClassifierConnection;
import org.geese.ci.classifier.store.StoreAccess;
import org.geese.config.Profile;

public class PosixAccess implements StoreAccess {

	private final Profile profile;

	public PosixAccess(Profile profile) {
		this.profile = profile;
	}
	
	@Override
	public ClassifierConnection open() throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
