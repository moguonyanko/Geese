package org.geese.ci.classifier.store.posix.dao;

import java.sql.SQLException;
import org.geese.ci.classifier.Feature;
import org.geese.ci.classifier.store.dao.FeatureCountDao;

public class PosixFeatureCountDao implements FeatureCountDao {

	@Override
	public boolean insert(Feature feature) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public double select(Feature feature) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int update(double count, Feature feature) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
