package org.geese.ci.classifier.db;

public enum StoreDef {

	FEATURECOUNT,
	CATEGORYCOUNT;

	public String getName(){
		return this.name().toLowerCase();
	}
}
