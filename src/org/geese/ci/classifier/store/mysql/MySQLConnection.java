package org.geese.ci.classifier.store.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.geese.ci.classifier.store.ClassifierConnection;
import org.geese.ci.classifier.store.StatementProvider;
import org.geese.config.Profile;


public class MySQLConnection implements ClassifierConnection, StatementProvider {

	private final Connection con;
	private final Profile profile;

	public MySQLConnection(Connection con, Profile profile) {
		this.con = con;
		this.profile = profile;
	}

	@Override
	public void init() throws SQLException {
		con.setAutoCommit(false);
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
	public void rollback() throws SQLException {
		con.rollback();
	}
	
	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException{
		return con.prepareStatement(sql);
	}
}
