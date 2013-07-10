package org.geese.ci.classifier.store;

public enum StoreDef {

	FEATURECOUNT,
	CATEGORYCOUNT;

	public String getName(){
		return this.name().toLowerCase();
	}
}
