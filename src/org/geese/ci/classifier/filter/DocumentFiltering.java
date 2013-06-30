package org.geese.ci.classifier.filter;

import java.util.*;
import java.util.regex.*;

public class DocumentFiltering implements WordFilterTask {

	private final Pattern splitter;
	private final WordFilter wordFilter;

	DocumentFiltering(WordFilter wordFilter, Pattern splitter) {
		this.wordFilter = wordFilter;
		this.splitter = splitter;
	}

	@Override
	public Map<String, Integer> get(String doc) {
		Map<String, Integer> result = new HashMap<>();
		String[] words = splitter.split(doc);

		for (String word : words) {
			if (wordFilter.accept(word)) {
				if (result.containsKey(word)) {
					int nowCount = result.get(word);
					result.put(word, nowCount + 1);
				} else {
					result.put(word, 1);
				}
			}
		}

		return result;
	}
}
