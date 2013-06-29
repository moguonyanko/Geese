package org.geese.ci.classifier.db.mysql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.geese.ci.classifier.Category;
import org.geese.ci.classifier.db.StoreDef;
import org.geese.ci.classifier.db.StoreElementDef;
import org.geese.ci.classifier.db.dao.CategoryCountDao;
import org.geese.ci.classifier.db.mysql.MySQLConnection;

public class MySQLCategoryCountDao implements CategoryCountDao {
	
	private final MySQLConnection con;
	
	private static final String TABLE = StoreDef.CATEGORYCOUNT.getName();

	private static final String SQL_INSERT;
	private static final String SQL_SELECT;
	private static final String SQL_UPDATE;
	private static final String SQL_DELETE;
	/* The category is primary key. */
	private static final String SQL_WHERE;
	
	static {
		String category = StoreElementDef.CATEGORY.getName();
		String count = StoreElementDef.COUNT.getName();
		SQL_INSERT = "INSERT INTO " + TABLE + " VALUES (?,?);";
		SQL_SELECT = "SELECT * FROM " + TABLE;
		SQL_UPDATE = "UPDATE " + TABLE + " SET " + count + "=?";
		SQL_DELETE = "DELETE FROM " + TABLE;
		/* The feature and category is primary key. */
		SQL_WHERE = " WHERE " + category + "=?;";
	}	

	public MySQLCategoryCountDao(MySQLConnection con) {
		this.con = con;
	}

	@Override
	public boolean insert(Category category) throws SQLException {
		boolean result = false;

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
