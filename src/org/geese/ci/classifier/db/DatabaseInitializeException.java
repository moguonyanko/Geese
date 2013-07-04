package org.geese.ci.classifier.db;

import org.geese.ci.classifier.InitializeException;

public class DatabaseInitializeException extends InitializeException{

	private final String errorDatabaseName;

	public DatabaseInitializeException(String errorDatabaseName, Throwable cause) {
		super(cause);
		this.errorDatabaseName = errorDatabaseName;
	}

	public String getErrorDatabaseName() {
		return errorDatabaseName;
	}
}
