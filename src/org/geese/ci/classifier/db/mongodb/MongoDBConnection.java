package org.geese.ci.classifier.db.mongodb;

import java.sql.SQLException;

import com.mongodb.Mongo;

import org.geese.ci.classifier.db.ClassifierConnection;

public class MongoDBConnection implements ClassifierConnection{

	private final Mongo conn;

	public MongoDBConnection(Mongo conn) {
		this.conn = conn;
	}
	
	@Override
	public void startTransaction() throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void close() throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void commit() throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void rollback() throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
