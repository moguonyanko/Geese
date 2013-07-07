package org.geese.config.bundle;

import org.geese.util.Logging;

/**
 * Default localization class.
 * 
 * This is default localization class file 
 * that is used when cannot found requested locale
 * properties.
 * Default locale "en_US" at this library.
 * 
 * @author moguonyanko
 */
public class Localization extends Localization_en_US {

	@Override
	protected Object[][] getContents() {
		Logging.warn("Cannot found requested bundle file and use default that.");
		
		return super.getContents();
	}
}