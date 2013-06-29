package org.geese.ci.classifier.db;

public enum StoreElementDef {

	FEATURE,
	CATEGORY,
	COUNT;

	public String getName() {
		return this.name().toLowerCase();
	}
}
