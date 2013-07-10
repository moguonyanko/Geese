package org.geese.ci.classifier.store.mysql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.geese.ci.classifier.Feature;
import org.geese.ci.classifier.store.StoreDef;
import org.geese.ci.classifier.store.StoreElementDef;
import org.geese.ci.classifier.store.dao.FeatureCountDao;
import org.geese.ci.classifier.store.mysql.MySQLConnection;

public class MySQLFeatureCountDao implements FeatureCountDao {

	private final MySQLConnection con;
	private static final String TABLE = StoreDef.FEATURECOUNT.getName();
	private static final String SQL_INSERT;
	private static final String SQL_SELECT;
	private static final String SQL_UPDATE;
	private static final String SQL_DELETE;
	/* The feature and category is primary key. */
	private static final String SQL_WHERE;

	static {
		String feature = StoreElementDef.FEATURE.getName();
		String category = StoreElementDef.CATEGORY.getName();
		String count = StoreElementDef.COUNT.getName();
		SQL_INSERT = "INSERT INTO " + TABLE + " VALUES (?,?,?);";
		SQL_SELECT = "SELECT * FROM " + TABLE;
		SQL_UPDATE = "UPDATE " + TABLE + " SET " + count + "=?";
		SQL_DELETE = "DELETE FROM " + TABLE;
		/* The feature and category is primary key. */
		SQL_WHERE = " WHERE " + feature + "=? AND " + category + "=?;";
	}

	public MySQLFeatureCountDao(MySQLConnection con) {
		this.con = con;
	}

	@Override
	public boolean insert(Feature feature) throws SQLException {
		boolean result = false;
		try (PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
			ps.setString(1, feature.getWord());
			ps.setString(2, feature.getCategoryName());
			ps.setDouble(3, 1.0);
			result = ps.execute();
		} catch (SQLException sqle) {
			throw sqle;
		}

		return result;
	}

	@Override
	public double select(Feature feature) throws SQLException {
		double count = 0;

		try (PreparedStatement ps = con.prepareStatement(SQL_SELECT + SQL_WHERE)) {
			ps.setString(1, feature.getWord());
			ps.setString(2, feature.getCategoryName());
			ResultSet rs = ps.executeQuery();
			rs.last();
			int row = rs.getRow();

			if (row > 0) {
				rs.first();
				count = rs.getDouble(3);
			}
		} catch (SQLException sqle) {
			throw sqle;
		}

		return count;
	}

	@Override
	public int update(double count, Feature feature) throws SQLException {
		int effectCount = 0;

		try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE + SQL_WHERE)) {
			ps.setDouble(1, count);
			ps.setString(2, feature.getWord());
			ps.setString(3, feature.getCategoryName());
			effectCount = ps.executeUpdate();
		} catch (SQLException sqle) {
			throw sqle;
		}

		return effectCount;
	}
}
