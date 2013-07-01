package org.geese.config;

public class ProfileInitializeException extends Exception {
	private final String errorPropertiesPath;

	public ProfileInitializeException(String errorPropPath, Throwable cause) {
		super(cause);
		this.errorPropertiesPath = errorPropPath;
	}

	public String getErrorPropertiesPath() {
		return errorPropertiesPath;
	}
}
