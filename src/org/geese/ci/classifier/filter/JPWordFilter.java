package org.geese.ci.classifier.filter;

import org.geese.util.Strings;

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
		
		if(Strings.isNullOrEmpty(doc)){
			return false;
		}
		
		return doc.length() > 1;
	}
}
