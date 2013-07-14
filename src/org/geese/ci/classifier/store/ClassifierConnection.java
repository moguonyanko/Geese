package org.geese.ci.classifier.store;

import java.io.IOException;
import java.sql.SQLException;

public interface ClassifierConnection extends AutoCloseable {

	void init() throws SQLException, IOException;
	
	@Override
	void close() throws SQLException, IOException;

	void commit() throws SQLException, IOException;

	void rollback() throws SQLException, IOException;
}
