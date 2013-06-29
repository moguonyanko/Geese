package org.geese.ci.classifier.db.dao;

import java.sql.SQLException;

import org.geese.ci.classifier.Feature;

public interface FeatureCountDao {

	boolean insert(Feature feature) throws SQLException;

	double select(Feature feature) throws SQLException;

	int update(double count, Feature feature) throws SQLException;
}
