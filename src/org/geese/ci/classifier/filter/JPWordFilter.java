package org.geese.ci.classifier.filter;

import org.geese.util.StringUtil;

/**
 * JP multi byte word filter.
 *
 * @todo
 * Exclude conjunction etc.
 * 
 */
public class JPWordFilter implements WordFilter {
	
	@Override
	public boolean accept(String doc) {
		
		if(StringUtil.isNullOrEmpty(doc)){
			return false;
		}
		
		return true;
	}
}
