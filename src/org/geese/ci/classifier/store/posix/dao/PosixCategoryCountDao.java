package org.geese.ci.classifier.store.posix.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.geese.ci.classifier.Category;
import org.geese.ci.classifier.store.dao.CategoryCountDao;
import org.geese.ci.classifier.store.posix.PosixConnection;

public class PosixCategoryCountDao implements CategoryCountDao {

	private final PosixConnection con;

	public PosixCategoryCountDao(PosixConnection con) {
		this.con = con;
	}
	
	@Override
	public boolean insert(Category category) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public double select(Category category) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set<String> findAllCategories() throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<Double> findAllCounts() throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int update(double count, Category category) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
