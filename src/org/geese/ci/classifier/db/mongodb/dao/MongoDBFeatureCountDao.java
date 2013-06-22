package org.geese.ci.classifier.db.mongodb.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import org.geese.ci.classifier.Feature;
import org.geese.ci.classifier.db.ClassifierConnection;
import org.geese.ci.classifier.db.dao.FeatureCountDao;
import org.geese.ci.classifier.db.mongodb.MongoDBConnection;

public class MongoDBFeatureCountDao extends FeatureCountDao {

	public MongoDBFeatureCountDao(ClassifierConnection connection) {
		super(connection);
	}

	@Override
	public boolean insert(Feature feature) {
		DBCollection dbColl = getDBCollection();

		DBObject dbObj = new BasicDBObject();
		dbObj.put(FEATURE, feature.getWord());
		dbObj.put(CATEGORY, feature.getCategoryName());
		dbObj.put(COUNT, 1.0);

		WriteResult result = dbColl.insert(dbObj);
		CommandResult cres = result.getLastError();
		boolean ok = cres.ok();

		return ok;
	}

	@Override
	public double select(Feature feature) {
		DBCollection dbColl = getDBCollection();
		DBCursor cursor = dbColl.find();

		if (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			Double count = (Double) dbObj.get(COUNT);
			return count;
		} else {
			return 0;
		}
	}

	@Override
	public int update(double count, Feature feature) {
		DBCollection dbColl = getDBCollection();

		DBObject keyObj = new BasicDBObject();
		keyObj.put(FEATURE, feature.getWord());
		keyObj.put(CATEGORY, feature.getCategoryName());

		BasicDBObject newCountObj = new BasicDBObject("$set", count);

		WriteResult result = dbColl.insert(keyObj, newCountObj);
		CommandResult cres = result.getLastError();
		boolean ok = cres.ok();

		return ok ? 1 : 0;
	}

	private DBCollection getDBCollection() {
		ClassifierConnection con = getConnection();
		MongoDBConnection mcon = (MongoDBConnection) con; /* @todo modify no cast */
		DB db = mcon.getDB();
		DBCollection coll = db.getCollection(TABLE);
		return coll;
	}
}
