package org.geese.ci.classifier.db;

import java.sql.SQLException;

public interface DBAccess {
	ClassifierConnection connect() throws SQLException;
	String getDBName();
}
