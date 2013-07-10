package org.geese.ci.classifier.store.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.geese.ci.classifier.Category;

public interface CategoryCountDao {

	boolean insert(Category category) throws SQLException;

	double select(Category category) throws SQLException;

	Set<String> findAllCategories() throws SQLException;

	List<Double> findAllCounts() throws SQLException;

	int update(double count, Category category) throws SQLException;
}
