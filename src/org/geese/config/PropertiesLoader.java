package org.geese.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	public static Properties load(String propPath) throws IOException {
		Properties props = new java.util.Properties();

		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(propPath);
		}
		props.load(stream);

		return props;
	}
}
