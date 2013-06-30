package org.geese.ci.classifier.filter;

import java.util.regex.Pattern;

public class FilterTaskFactory {

	public static WordFilterTask createWordFilterTask(WordFilter customFilter, Pattern splitter) {
		WordFilterTask task = new DocumentFiltering(customFilter, splitter);
		return task;
	}
}
