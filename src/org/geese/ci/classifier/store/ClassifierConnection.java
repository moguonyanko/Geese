package org.geese.ci.classifier.store;

import java.sql.SQLException;

public interface ClassifierConnection extends AutoCloseable {

	void init() throws SQLException;
	
	@Override
	void close() throws SQLException;

	void commit() throws SQLException;

	void rollback() throws SQLException;
}
