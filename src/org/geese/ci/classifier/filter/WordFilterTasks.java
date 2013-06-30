package org.geese.ci.classifier.filter;

import java.util.regex.Pattern;

public enum WordFilterTasks {

	DEFAULT(Pattern.compile("[\\s\\p{Punct}]")) {
		@Override
		public WordFilterTask getTask() {
			WordFilter defaultFilter = new DefaultWordFilter();
			WordFilterTask task = FilterTaskFactory.createWordFilterTask(defaultFilter, splitter);
			
			return task;
		}
	},
	JP(Pattern.compile("[「」、。\\s\\p{Punct}]")) {
		@Override
		public WordFilterTask getTask() {
			WordFilter multiByteFilter = new JPWordFilter();
			WordFilterTask task = FilterTaskFactory.createWordFilterTask(multiByteFilter, splitter);

			return task;
		}
	};
	
	final Pattern splitter;

	private WordFilterTasks(Pattern splitter) {
		this.splitter = splitter;
	}

	public abstract WordFilterTask getTask();
}
