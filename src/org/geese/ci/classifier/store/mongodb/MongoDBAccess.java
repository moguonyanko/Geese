package org.geese.ci.classifier.store.mongodb;

import java.net.UnknownHostException;
import java.sql.ClientInfoStatus;
import java.sql.SQLClientInfoException;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.Mongo;

import org.geese.ci.classifier.store.ClassifierConnection;
import org.geese.ci.classifier.store.StoreAccess;
import org.geese.config.Profile;

/**
 * NOSQL access data.
 * 
 */
public class MongoDBAccess implements StoreAccess {

	public static final String STORE_NAME = "MongoDB";
	
	private final String hostname;
	private final int port;
	private final Profile profile;

	public MongoDBAccess(Profile profile) {
		this.profile = profile;
		hostname = profile.getHost();
		port = profile.getPort();
	}

	@Override
	public ClassifierConnection open() throws SQLClientInfoException {
		
		Mongo con;
		
		try {
			con = new Mongo(hostname, port);
		} catch (UnknownHostException ex) {
			String errMsg = "Unknown host[" + hostname + "] or port[" + port + "].";
			Map<String, ClientInfoStatus> errInfo = new HashMap<>();
			errInfo.put(errMsg, ClientInfoStatus.REASON_UNKNOWN_PROPERTY);
			throw new SQLClientInfoException(errMsg, errInfo, ex);
		}
		
		ClassifierConnection cc = new MongoDBConnection(con, profile);
		
		return cc;
	}
}
