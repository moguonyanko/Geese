package org.geese.ci.classifier.store.posix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.geese.ci.classifier.store.ClassifierConnection;
import org.geese.config.Profile;

public class PosixConnection implements ClassifierConnection {

	private final Path rootPath;
	private final Profile profile;
	private Path storePath;

	public PosixConnection(Path rootPath, Profile profile) {
		this.rootPath = rootPath;
		this.profile = profile;
	}

	@Override
	public void init() throws IOException {
		String storeName = profile.getStoreName();
		Path logicalStorePath = Paths.get(rootPath.toString(), storeName);
		Path realStorePath = logicalStorePath.toRealPath(LinkOption.NOFOLLOW_LINKS);

		storePath = Files.createDirectories(realStorePath);
	}

	@Override
	public void close() {
	}

	@Override
	public void commit() {
	}

	@Override
	public void rollback() {
	}

	public final Path getStorePath() {
		return storePath;
	}
}
