package org.geese.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class PropertiesLoader{

	public static Properties load(String propertiesPath) throws IOException{
		Properties props = new java.util.Properties();

		Path path = FileSystems.getDefault().getPath(propertiesPath);
		InputStream stream = Files.newInputStream(path, StandardOpenOption.READ);
		props.load(stream);

		return props;
	}
}
