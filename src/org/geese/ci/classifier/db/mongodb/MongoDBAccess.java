package org.geese.ci.classifier.db.mongodb;

import java.net.UnknownHostException;
import java.sql.SQLException;

import com.mongodb.Mongo;

import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.ci.classifier.db.DBAccess;
import org.geese.config.Profile;

/**
 * NOSQL access data.
 * 
 */
public class MongoDBAccess implements DBAccess {

	public static final String DATABASE_NAME = "MongoDB";
	
	private final String hostname;
	private final int port;
	private final Profile profile;

	public MongoDBAccess(Profile profile) {
		this.profile = profile;
		hostname = profile.getDatabaseHost();
		port = profile.getDatabasePort();
	}

	@Override
	public ClassifierConnection connect() throws SQLException {
		
		Mongo con;
		
		try {
			con = new Mongo(hostname, port);
		} catch (UnknownHostException ex) {
			throw new SQLException(ex.getMessage());
		}
		
		ClassifierConnection cc = new MongoDBConnection(con, profile);
		
		return cc;
	}
}
