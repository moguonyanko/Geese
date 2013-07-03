package org.geese.config;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import org.geese.util.Strings;

public class Profile {

	private final Properties props;
	private final ResourceBundle bundle;

	public Profile(String configPath) throws ProfileInitializeException {
		try {
			props = PropertiesLoader.load(configPath);

			String bundleName = ".localization";
			String bundlePath = this.getClass().getPackage().getName() + bundleName;
			String language = props.getProperty("locale.language");
			String country = props.getProperty("locale.country");
			Locale bundleLocale;

			if (!Strings.isNullOrEmpty(language) && !Strings.isNullOrEmpty(country)) {
				bundleLocale = new Locale(language, country);
			} else {
				bundleLocale = Locale.getDefault();
			}

			bundle = ResourceBundle.getBundle(bundlePath, bundleLocale);
		} catch (IOException ex) {
			throw new ProfileInitializeException(configPath, ex);
		}
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	private String getValue(String key) {
		if (key != null && !key.isEmpty()) {
			return props.getProperty(key);
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
