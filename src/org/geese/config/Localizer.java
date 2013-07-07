package org.geese.config;

import java.util.Locale;
import java.util.ResourceBundle;
import static java.util.ResourceBundle.Control.TTL_DONT_CACHE;

import org.geese.config.bundle.Localization;

public class Localizer {

	private final ResourceBundle bundle;
	private static final String BUNDLE_NAME = Localization.class.getName();

	Localizer(Locale bundleLocale) {
		ResourceBundle.Control control = new ResourceBundle.Control() {
			@Override
			public long getTimeToLive(String baseName, Locale locale) {
				return TTL_DONT_CACHE;
			}
		};

		this.bundle = ResourceBundle.getBundle(BUNDLE_NAME, bundleLocale, control);
	}

	public final String getString(String key) {
		return bundle.getString(key);
	}
}
