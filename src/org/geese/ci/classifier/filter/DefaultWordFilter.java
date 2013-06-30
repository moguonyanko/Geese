package org.geese.ci.classifier.filter;

import org.geese.util.StringUtil;

public class DefaultWordFilter implements WordFilter {

	@Override
	public boolean accept(String doc) {
		
		if(StringUtil.isNullOrEmpty(doc)){
			return false;
		}

		int wordLength = doc.length();

		return 2 < wordLength && wordLength < 20;
	}

}
