package org.geese.ci.classifier.store;

import org.geese.ci.classifier.InitializeException;

public class StoreInitializeException extends InitializeException{

	private final String errorDatabaseName;

	public StoreInitializeException(String errorDatabaseName, Throwable cause) {
		super(cause);
		this.errorDatabaseName = errorDatabaseName;
	}

	public String getErrorDatabaseName() {
		return errorDatabaseName;
	}
}
