package org.geese.config;

import java.util.Locale;
import java.util.ResourceBundle;
import static java.util.ResourceBundle.Control.TTL_DONT_CACHE;

public class Localizer {

	private final ResourceBundle bundle;
	private static final String BUNDLE_NAME = "Localization";

	Localizer(Locale bundleLocale) {
		String bundlePath = this.getClass().getPackage().getName() + "." + BUNDLE_NAME;

		ResourceBundle.Control control = new ResourceBundle.Control() {
			@Override
			public long getTimeToLive(String baseName, Locale locale) {
				return TTL_DONT_CACHE;
			}
		};

		this.bundle = ResourceBundle.getBundle(bundlePath, bundleLocale, control);
	}

	public final String getString(String key) {
		return bundle.getString(key);
	}
}
