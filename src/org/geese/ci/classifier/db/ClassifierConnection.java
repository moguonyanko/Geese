package org.geese.ci.classifier.db;

import java.sql.SQLException;
import org.geese.config.Profile;

public interface ClassifierConnection extends AutoCloseable {

	void init() throws SQLException;
	
	@Override
	void close() throws SQLException;

	void commit() throws SQLException;

	void rollback() throws SQLException;
}
