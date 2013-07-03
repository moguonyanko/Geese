package org.geese.ci.classifier.db.mongodb;

import com.mongodb.Mongo;
import com.mongodb.DB;

import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.config.Profile;

public class MongoDBConnection implements ClassifierConnection{

	private final Mongo conn;
	private final DB db;
	
	private final Profile profile;

	public MongoDBConnection(Mongo conn, Profile profile) {
		this.conn = conn;
		this.profile = profile;
		String dbName = profile.getDatabaseName();
		db = conn.getDB(dbName);
	}
	
	@Override
	public void init() {
		//String userId = profile.getDatabaseUserName();
		//String password = profile.getDatabaseUserPassword();
		//db.authenticate(userId, password.toCharArray());
	}

	@Override
	public void close() {
		conn.close();
	}

	@Override
	public void commit() {
		/* Does nothing now. */
	}

	@Override
	public void rollback() {
		/* Does nothing now. */
	}
	
	public DB getDB(){
		return db;
	}
}
