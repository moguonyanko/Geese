package org.geese.util;

import java.util.Properties;

import org.geese.loader.ConfigLoader;

public class ConfigUtil {
	private static final Properties properties = ConfigLoader.load();

	public static String getValue(String key){
		if(key != null && !key.isEmpty()){
			return properties.getProperty(key);
		}else{
			throw new IllegalArgumentException("Invalid key recieved. : " + key);
		}
	}
}
