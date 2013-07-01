package org.geese.ci.classifier.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.ci.classifier.db.DBAccess;

import org.geese.config.Profile;

/**
 * RDBMS Access data.
 *
 */
public class MySQLAccess implements DBAccess {

	public static final String DATABASE_NAME = "MySQL";
	
	private final String jdbcUrl;
	private final String userId;
	private final String password;
	
	private final Profile profile;

	public MySQLAccess(Profile profile) {
		this.profile = profile;
		
		String name = profile.getDatabaseTypeName();
		String host = profile.getDatabaseHost();
		int port = profile.getDatabasePort();
		String database = profile.getDatabaseName();

		StringBuilder url = new StringBuilder("jdbc:");
		url.append(name).append(":").append("//");
		url.append(host).append(":").append(port).append("/");
		url.append(database);
		jdbcUrl = url.toString();

		userId = profile.getDatabaseUserName();
		password = profile.getDatabaseUserPassword();
	}

	@Override
	public ClassifierConnection connect() throws SQLException {
		Connection con = DriverManager.getConnection(jdbcUrl, userId, password);
		ClassifierConnection cc = new MySQLConnection(con, profile);

		return cc;
	}
}
