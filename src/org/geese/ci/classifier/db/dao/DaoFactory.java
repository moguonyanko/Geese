package org.geese.ci.classifier.db.dao;

import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.ci.classifier.db.mongodb.MongoDBAccess;
import org.geese.ci.classifier.db.mysql.MySQLAccess;
import org.geese.ci.classifier.db.mongodb.dao.MongoDBCategoryCountDao;
import org.geese.ci.classifier.db.mongodb.dao.MongoDBFeatureCountDao;
import org.geese.ci.classifier.db.mysql.dao.MySQLCategoryCountDao;
import org.geese.ci.classifier.db.mysql.dao.MySQLFeatureCountDao;

public class DaoFactory {

	public static FeatureCountDao createFeatureCountDao(String dbName, ClassifierConnection connection) {
		if (dbName == null) {
			throw new IllegalArgumentException("Database name is null.");
		}

		if (dbName.equalsIgnoreCase(MySQLAccess.DBACCESS.getDBName())) {
			return new MySQLFeatureCountDao(connection);
		} else if (dbName.equalsIgnoreCase(MongoDBAccess.DBACCESS.getDBName())) {
			return new MongoDBFeatureCountDao(connection);
		} else {
			throw new UnsupportedOperationException("Unsupported database type requested.");
		}
	}

	public static CategoryCountDao createCategoryCountDao(String dbType, ClassifierConnection connection) {
		if (dbType == null) {
			throw new IllegalArgumentException("Database type is null.");
		}

		if (dbType.equalsIgnoreCase(MySQLAccess.DBACCESS.getDBName())) {
			return new MySQLCategoryCountDao(connection);
		} else if (dbType.equalsIgnoreCase(MongoDBAccess.DBACCESS.getDBName())) {
			return new MongoDBCategoryCountDao(connection);
		} else {
			throw new UnsupportedOperationException("Unsupported database type requested.");
		}
	}
}
