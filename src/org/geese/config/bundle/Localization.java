package org.geese.config.bundle;

import java.util.ListResourceBundle;

/**
 * Default localization class.
 * 
 * 
 * @author moguonyanko
 */
public class Localization extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {

		return new Object[][]{
			{"error.start", "Failed at connection to classifier database."}
		};
	}
}
