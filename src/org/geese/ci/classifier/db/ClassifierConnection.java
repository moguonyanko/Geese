package org.geese.ci.classifier.db;

import java.sql.SQLException;

public interface ClassifierConnection extends AutoCloseable {

	void startTransaction() throws SQLException;
	
	@Override
	void close() throws SQLException;

	void commit() throws SQLException;

	void rollback() throws SQLException;
}
