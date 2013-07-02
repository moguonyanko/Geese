package org.geese.config;

import java.io.IOException;
import java.util.Properties;

public class Profile {

	private final Properties properties;

	public Profile(String configPath) throws ProfileInitializeException {
		try {
			properties = PropertiesLoader.load(configPath);
		} catch (IOException ex) {
			throw new ProfileInitializeException(configPath, ex);
		}
	}

	private String getValue(String key) {
		if (key != null && !key.isEmpty()) {
			return properties.getProperty(key);
		} else {
			throw new IllegalArgumentException("Invalid key recieved. : " + key);
		}
	}

	public String getDatabaseTypeName() {
		return getValue("db.name");
	}

	public int getDatabasePort() {
		String portValue = getValue("db.port");
		return Integer.parseInt(portValue);
	}

	public String getDatabaseHost() {
		return getValue("db.host");
	}

	public String getDatabaseName() {
		return getValue("db.database");
	}

	public String getDatabaseUserName() {
		return getValue("db.user");
	}

	public String getDatabaseUserPassword() {
		return getValue("db.password");
	}
}
