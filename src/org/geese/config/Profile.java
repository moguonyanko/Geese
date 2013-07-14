package org.geese.config;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	private Locale getLocale() {
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

	public final String getStoreType() {
		return getValue("store.type");
	}

	public final int getPort() {
		String portValue = getValue("store.port");
		return Integer.parseInt(portValue);
	}

	public final String getHost() {
		return getValue("store.host");
	}

	public final String getStoreName() {
		return getValue("store.name");
	}

	public final String getStoreUser() {
		return getValue("store.user");
	}

	public final String getStorePassword() {
		return getValue("store.password");
	}

	public final Path getPath() {
		String pathParam = getValue("store.path");
		Path path = Paths.get(pathParam);

		return path;
	}
}
