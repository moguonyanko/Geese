package org.geese.ci.classifier.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.ci.classifier.db.DBAccess;

import org.geese.util.ConfigUtil;

/**
 * RDBMS Access data.
 *
 */
public enum MySQLAccess implements DBAccess {

	DBACCESS;
	
	private final String jdbcUrl;
	private final String userId;
	private final String password;

	private final String DBNAME = "MySQL";
	
	private MySQLAccess() {
		String name = ConfigUtil.getValue("db.name");
		String host = ConfigUtil.getValue("db.host");
		String port = ConfigUtil.getValue("db.port");
		String database = ConfigUtil.getValue("db.database");

		StringBuilder url = new StringBuilder("jdbc:");
		url.append(name).append(":").append("//");
		url.append(host).append(":").append(port).append("/");
		url.append(database);
		jdbcUrl = url.toString();

		userId = ConfigUtil.getValue("db.user");
		password = ConfigUtil.getValue("db.password");
	}

	@Override
	public ClassifierConnection connect() throws SQLException {
		Connection con = DriverManager.getConnection(jdbcUrl, userId, password);
		ClassifierConnection cc = new MySQLConnection(con);

		return cc;
	}

	@Override
	public String getDBName(){
		return DBNAME;
	}
}
