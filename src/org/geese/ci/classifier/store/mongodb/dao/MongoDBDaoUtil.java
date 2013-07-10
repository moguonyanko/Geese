package org.geese.ci.classifier.store.mongodb.dao;

import org.geese.ci.classifier.Category;
import org.geese.ci.classifier.Feature;

public class MongoDBDaoUtil{

	private static final String OBJECTID = "_id";

	static String getObjectId(Feature feature){
		return feature.hashCode() + "_" + feature.hashCode();
	}

	static String getObjectId(Category category){
		return String.valueOf(category.hashCode());
	}

	static String getObjectIdName(){
		return OBJECTID;
	}
}
