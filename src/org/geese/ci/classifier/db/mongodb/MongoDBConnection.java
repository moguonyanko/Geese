package org.geese.ci.classifier.db.mongodb;

import java.sql.SQLException;

import com.mongodb.Mongo;
import com.mongodb.DB;

import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.ci.classifier.util.ConfigUtil;

public class MongoDBConnection implements ClassifierConnection{

	private final Mongo conn;
	private DB db;

	public MongoDBConnection(Mongo conn) {
		this.conn = conn;
	}
	
	@Override
	public void init() throws SQLException {
		String dbName = ConfigUtil.getValue("db.database");
		db = conn.getDB(dbName);
		
		//String userId = ConfigUtil.getValue("db.user");
		//String password = ConfigUtil.getValue("db.password");
		//db.authenticate(userId, password.toCharArray());
	}

	@Override
	public void close() throws SQLException {
		db = null;
		conn.close();
	}

	@Override
	public void commit() throws SQLException {
	}

	@Override
	public void rollback() throws SQLException {
	}
	
	public DB getDB(){
		return db;
	}
}
