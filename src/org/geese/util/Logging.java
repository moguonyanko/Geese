package org.geese.util;

import java.util.logging.Logger;

public class Logging {

	private static final Logger logger;
	private static final String LOG_SEPARATOR = ":";

	static {
		logger = Logger.getLogger(Logging.class.getName());
	}

	public static void error(String txt) {
		logger.severe(txt);
	}

	public static void error(Throwable t) {
		logger.severe(t.getMessage());
	}

	public static void error(String txt, Throwable t) {
		StringBuilder msg = new StringBuilder(txt);
		msg.append(LOG_SEPARATOR).append(t.getMessage());

		logger.severe(msg.toString());
	}

	public static void info(String txt) {
		logger.info(txt);
	}

	public static void warn(String txt) {
		logger.warning(txt);
	}
}
