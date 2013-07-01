package org.geese.util;

import java.util.logging.Logger;

public class Logging {

	private static final Logger logger;

	static {
		logger = Logger.getLogger(Logging.class.getName());
	}

	public static void error(String txt) {
		logger.severe(txt);
	}

	public static void error(Throwable t) {
		logger.severe(t.getMessage());
	}

	public static void info(String txt) {
		logger.info(txt);
	}

	public static void warn(String txt) {
		logger.warning(txt);
	}
}
