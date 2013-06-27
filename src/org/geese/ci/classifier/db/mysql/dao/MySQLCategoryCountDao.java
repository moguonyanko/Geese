package org.geese.ci.classifier.db.mysql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.geese.ci.classifier.Category;
import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.ci.classifier.db.dao.CategoryCountDao;
import org.geese.ci.classifier.db.mysql.MySQLConnection;

public class MySQLCategoryCountDao extends CategoryCountDao {

	private static final String SQL_INSERT = "INSERT INTO " + STORE + " VALUES (?,?);";
	private static final String SQL_SELECT = "SELECT * FROM " + STORE;
	private static final String SQL_UPDATE = "UPDATE " + STORE + " SET count=?";
	private static final String SQL_DELETE = "DELETE FROM " + STORE;
	/* The category is primary key. */
	private static final String SQL_WHERE = " WHERE category=?;";

	public MySQLCategoryCountDao(ClassifierConnection connection) {
		super(connection);
	}

	@Override
	public boolean insert(Category category) throws SQLException {
		boolean result = false;
		MySQLConnection con = (MySQLConnection)getConnection();

		try (PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
			ps.setString(1, category.getName());
			ps.setDouble(2, 1.0);
			result = ps.execute();
		} catch (SQLException sqle) {
			throw sqle;
		}

		return result;
	}

	@Override
	public double select(Category category) throws SQLException {
		double count = 0;
		MySQLConnection con = (MySQLConnection)getConnection();

		try (PreparedStatement ps = con.prepareStatement(SQL_SELECT + SQL_WHERE)) {
			ps.setString(1, category.getName());
			ResultSet rs = ps.executeQuery();
			rs.last();
			int row = rs.getRow();

			if (row > 0) {
				rs.first();
				count = rs.getDouble(2);
			}
		} catch (SQLException sqle) {
			throw sqle;
		}

		return count;
	}

	@Override
	public Set<String> findAllCategories() throws SQLException {
		Set<String> result = new HashSet<>();
		MySQLConnection con = (MySQLConnection)getConnection();

		try (PreparedStatement ps = con.prepareStatement(SQL_SELECT + ";")) {
			ResultSet rs = ps.executeQuery();
			rs.last();
			int row = rs.getRow();

			if (row > 0) {
				rs.first();
				while (rs.next()) {
					result.add(rs.getString(1)); /* Hold memory as category type number. */
				}
			}
		} catch (SQLException sqle) {
			throw sqle;
		}

		return result;
	}

	@Override
	public List<Double> findAllCounts() throws SQLException {
		List<Double> counts = new ArrayList<>();
		MySQLConnection con = (MySQLConnection)getConnection();

		try (PreparedStatement ps = con.prepareStatement(SQL_SELECT + ";")) {
			ResultSet rs = ps.executeQuery();
			rs.last();
			int row = rs.getRow();

			if (row > 0) {
				rs.first();
				while (rs.next()) {
					counts.add(rs.getDouble(2)); /* Hold memory as category type number. */
				}
			}
		} catch (SQLException sqle) {
			throw sqle;
		}

		return counts;
	}

	@Override
	public int update(double count, Category category) throws SQLException {
		int effectCount = 0;
		MySQLConnection con = (MySQLConnection)getConnection();

		try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE + SQL_WHERE)) {
			ps.setDouble(1, count);
			ps.setString(2, category.getName());
			effectCount = ps.executeUpdate();
		} catch (SQLException sqle) {
			throw sqle;
		}

		return effectCount;
	}
}
