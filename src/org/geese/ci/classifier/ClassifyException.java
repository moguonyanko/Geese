package org.geese.ci.classifier;

import java.sql.SQLTransientException;

public class ClassifyException extends SQLTransientException {

	public ClassifyException(String message) {
		super(message);
	}
}
