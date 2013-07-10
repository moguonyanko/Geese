package org.geese.ci.classifier.store;

public enum StoreElementDef {

	FEATURE,
	CATEGORY,
	COUNT;

	public String getName() {
		return this.name().toLowerCase();
	}
}
