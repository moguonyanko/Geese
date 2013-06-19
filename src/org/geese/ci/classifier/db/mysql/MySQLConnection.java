package org.geese.ci.classifier.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.geese.ci.classifier.db.ClassifierConnection;


public class MySQLConnection implements ClassifierConnection {

	private final Connection con;

	public MySQLConnection(Connection con) {
		this.con = con;
	}

	@Override
	public void close() throws SQLException {
		con.close();
	}

	@Override
	public void commit() throws SQLException {
		con.commit();
	}

	@Override
	public void startTransaction() throws SQLException {
		con.setAutoCommit(false);
	}

	@Override
	public void rollback() throws SQLException {
		con.rollback();
	}
	
	public PreparedStatement prepareStatement(String sql) throws SQLException{
		return con.prepareStatement(sql);
	}
}
