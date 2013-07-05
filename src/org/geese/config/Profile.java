package org.geese.config;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.geese.util.Strings;

public class Profile {

	private final Properties props;
	private final Localizer localizer;

	public Profile(String configPath) throws ProfileInitializeException {
		try {
			props = PropertiesLoader.load(configPath);
			localizer = new Localizer(getLocale());
		} catch (IOException ex) {
			throw new ProfileInitializeException(configPath, ex);
		}
	}

	private String getValue(String key) {
		if (key != null && !key.isEmpty()) {
			return props.getProperty(key);
		} else {
			throw new IllegalArgumentException("Invalid key recieved. : " + key);
		}
	}

	public final String toLocalize(String key) {
		return localizer.getString(key);
	}

	public final Locale getLocale() {
		String language = getValue("locale.language");
		String country = getValue("locale.country");
		Locale bundleLocale;

		if (!Strings.isNullOrEmpty(language) && !Strings.isNullOrEmpty(country)) {
			bundleLocale = new Locale(language, country);
		} else {
			bundleLocale = Locale.getDefault();
		}

		return bundleLocale;
	}

	public final String getDatabaseTypeName() {
		return getValue("db.name");
	}

	public final int getDatabasePort() {
		String portValue = getValue("db.port");
		return Integer.parseInt(portValue);
	}

	public final String getDatabaseHost() {
		return getValue("db.host");
	}

	public final String getDatabaseName() {
		return getValue("db.database");
	}

	public final String getDatabaseUserName() {
		return getValue("db.user");
	}

	public final String getDatabaseUserPassword() {
		return getValue("db.password");
	}
}
