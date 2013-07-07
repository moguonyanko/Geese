package org.geese.config.bundle;

import java.util.ListResourceBundle;

public class Localization_en_US extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		
		return new Object[][]{
			{"error.start", "Failed at connection to classifier database."}
		};
	}
}
