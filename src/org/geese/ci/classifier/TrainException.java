package org.geese.ci.classifier;

/**
 * An exception that provides infomation on a trainning classifier.
 *
 */
public class TrainException extends OperateException {

	private String errorWord;
	private String errorCategoryName;

	public TrainException(String errorWord, String errorCategoryName, String reason) {
		super(reason);
		this.errorWord = errorWord;
		this.errorCategoryName = errorCategoryName;
	}

	public TrainException(String reason) {
		super(reason);
	}

	public final String getErrorWord() {
		return errorWord;
	}

	public final String getErrorCategoryName() {
		return errorCategoryName;
	}
}
