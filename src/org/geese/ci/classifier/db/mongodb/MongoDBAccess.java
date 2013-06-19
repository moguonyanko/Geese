package org.geese.ci.classifier.db.mongodb;

import java.net.UnknownHostException;
import java.sql.SQLException;

import com.mongodb.Mongo;

import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.ci.classifier.db.DBAccess;
import org.geese.ci.classifier.util.ConfigUtil;

/**
 * NOSQL access data.
 * 
 */
public enum MongoDBAccess implements DBAccess {

	DBACCESS;
	
	private final String DBNAME = "MongoDB";
	
	private final String hostname;
	private final int port;

	private MongoDBAccess() {
		hostname = ConfigUtil.getValue("db.host");
		String _port = ConfigUtil.getValue("db.port");
		port = Integer.parseInt(_port); /* @todo handle exception */
	}

	@Override
	public ClassifierConnection connect() throws SQLException {
		
		Mongo con;
		
		try {
			con = new Mongo(hostname, port);
		} catch (UnknownHostException ex) {
			throw new SQLException(ex.getMessage());
		}
		
		ClassifierConnection cc = new MongoDBConnection(con);
		
		return cc;
	}

	@Override
	public String getDBName(){
		return DBNAME;
	}
}
