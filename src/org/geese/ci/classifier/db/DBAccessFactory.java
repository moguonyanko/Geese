package org.geese.ci.classifier.db;

import org.geese.ci.classifier.db.mongodb.MongoDBAccess;
import org.geese.ci.classifier.db.mysql.MySQLAccess;
import org.geese.config.Profile;

public class DBAccessFactory {

	public static DBAccess create(Profile profile) {
		String dbName = profile.getDatabaseTypeName();

		if (dbName == null) {
			throw new IllegalArgumentException("Database name is null.");
		}

		if (dbName.equalsIgnoreCase(MySQLAccess.DATABASE_NAME)) {
			return new MySQLAccess(profile);
		} else if (dbName.equalsIgnoreCase(MongoDBAccess.DATABASE_NAME)) {
			return new MongoDBAccess(profile);
		} else {
			throw new UnsupportedOperationException("Unsupported database name requested.");
		}
	}
}
