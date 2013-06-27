package org.geese.ci.classifier.db.dao;

import java.sql.SQLException;

import org.geese.ci.classifier.Feature;
import org.geese.ci.classifier.db.ClassifierConnection;

public abstract class FeatureCountDao extends ClassifierDao{

	protected static final String STORE = "featurecount";
	protected static final String FEATURE = "feature";
	protected static final String CATEGORY = "category";
	protected static final String COUNT = "count";
	
	public FeatureCountDao(ClassifierConnection connection) {
		super(connection);
	}
	
	public abstract boolean insert(Feature feature) throws SQLException;
	public abstract double select(Feature feature) throws SQLException;
	public abstract int update(double count, Feature feature) throws SQLException;
	
}
