package org.geese.ci.classifier.store.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.geese.ci.classifier.store.ClassifierConnection;
import org.geese.ci.classifier.store.StoreAccess;
import org.geese.config.Profile;

/**
 * RDBMS Access data.
 *
 */
public class MySQLAccess implements StoreAccess {

	public static final String STORE_NAME = "MySQL";
	
	private final String jdbcUrl;
	private final String userId;
	private final String password;
	private final Profile profile;

	public MySQLAccess(Profile profile) {
		this.profile = profile;

		String name = profile.getStoreType();
		String host = profile.getHost();
		int port = profile.getPort();
		String database = profile.getStoreName();

		StringBuilder url = new StringBuilder("jdbc:");
		url.append(name).append(":").append("//");
		url.append(host).append(":").append(port).append("/");
		url.append(database);
		jdbcUrl = url.toString();

		userId = profile.getStoreUser();
		password = profile.getStorePassword();
	}

	@Override
	public ClassifierConnection open() throws SQLException {
		Connection con = DriverManager.getConnection(jdbcUrl, userId, password);
		ClassifierConnection cc = new MySQLConnection(con, profile);

		return cc;
	}
}
