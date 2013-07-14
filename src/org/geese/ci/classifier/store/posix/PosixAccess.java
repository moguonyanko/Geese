package org.geese.ci.classifier.store.posix;

import java.nio.file.Path;

import org.geese.ci.classifier.store.ClassifierConnection;
import org.geese.ci.classifier.store.StoreAccess;
import org.geese.config.Profile;

public class PosixAccess implements StoreAccess {

	private final Profile profile;

	public PosixAccess(Profile profile) {
		this.profile = profile;
	}
	
	@Override
	public ClassifierConnection open() {
		Path path = profile.getPath();
		ClassifierConnection con = new PosixConnection(path, profile);
		return con;
	}
	
}
