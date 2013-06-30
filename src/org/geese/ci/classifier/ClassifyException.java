package org.geese.ci.classifier;

import java.sql.SQLTransientException;

/**
 * An exception that provides infomation on a classification error.
 * 
 */
public class ClassifyException extends SQLTransientException {

	private final String errorWord;
	private final String errorCategoryName;
	
	public ClassifyException(String errorWord, String errorCategoryName, String reason) {
		super(reason);
		this.errorWord = errorWord;
		this.errorCategoryName = errorCategoryName;
	}

	public String getErrorWord() {
		return errorWord;
	}

	public String getErrorCategoryName() {
		return errorCategoryName;
	}
}
