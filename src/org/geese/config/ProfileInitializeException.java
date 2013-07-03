package org.geese.config;

import org.geese.ci.classifier.InitializeException;

public class ProfileInitializeException extends InitializeException {
	private final String errorPropertiesPath;

	public ProfileInitializeException(String errorPropPath, Throwable cause) {
		super(cause);
		this.errorPropertiesPath = errorPropPath;
	}

	public String getErrorPropertiesPath() {
		return errorPropertiesPath;
	}
}
