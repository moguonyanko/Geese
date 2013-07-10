package org.geese.ci.classifier.store.dao;

import org.geese.ci.classifier.store.ClassifierConnection;
import org.geese.ci.classifier.store.mongodb.MongoDBAccess;
import org.geese.ci.classifier.store.mongodb.MongoDBConnection;
import org.geese.ci.classifier.store.mysql.MySQLAccess;
import org.geese.ci.classifier.store.mongodb.dao.MongoDBCategoryCountDao;
import org.geese.ci.classifier.store.mongodb.dao.MongoDBFeatureCountDao;
import org.geese.ci.classifier.store.mysql.MySQLConnection;
import org.geese.ci.classifier.store.mysql.dao.MySQLCategoryCountDao;
import org.geese.ci.classifier.store.mysql.dao.MySQLFeatureCountDao;

public class DaoFactory {

	public static FeatureCountDao createFeatureCountDao(String dbName,
		ClassifierConnection connection) {
		if (dbName == null) {
			throw new IllegalArgumentException("Database name is null.");
		}

		if (dbName.equalsIgnoreCase(MySQLAccess.STORE_NAME)) {
			return new MySQLFeatureCountDao((MySQLConnection) connection);
		} else if (dbName.equalsIgnoreCase(MongoDBAccess.STORE_NAME)) {
			return new MongoDBFeatureCountDao((MongoDBConnection) connection);
		} else {
			throw new UnsupportedOperationException("Unsupported database type requested.");
		}
	}

	public static CategoryCountDao createCategoryCountDao(String dbType,
		ClassifierConnection connection) {
		if (dbType == null) {
			throw new IllegalArgumentException("Database type is null.");
		}

		if (dbType.equalsIgnoreCase(MySQLAccess.STORE_NAME)) {
			return new MySQLCategoryCountDao((MySQLConnection) connection);
		} else if (dbType.equalsIgnoreCase(MongoDBAccess.STORE_NAME)) {
			return new MongoDBCategoryCountDao((MongoDBConnection) connection);
		} else {
			throw new UnsupportedOperationException("Unsupported database type requested.");
		}
	}
}
