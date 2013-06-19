package org.geese.ci.classifier.db;

import org.geese.ci.classifier.db.mongodb.MongoDBAccess;
import org.geese.ci.classifier.db.mysql.MySQLAccess;

public class DBAccessFactory {

	public static DBAccess create(String dbName) {

		if (dbName == null) {
			throw new IllegalArgumentException("Database name is null.");
		}

		if (dbName.equalsIgnoreCase(MySQLAccess.DBACCESS.getDBName())) {
			return MySQLAccess.DBACCESS;
		} else if (dbName.equalsIgnoreCase(MongoDBAccess.DBACCESS.getDBName())) {
			return MongoDBAccess.DBACCESS;
		} else {
			throw new UnsupportedOperationException("Unsupported database name requested.");
		}
	}
}
