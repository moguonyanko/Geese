package org.geese.ci.classifier.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.ci.classifier.db.StatementProvider;


public class MySQLConnection implements ClassifierConnection, StatementProvider {

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
	public void init() throws SQLException {
		con.setAutoCommit(false);
	}

	@Override
	public void rollback() throws SQLException {
		con.rollback();
	}
	
	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException{
		return con.prepareStatement(sql);
	}
}
