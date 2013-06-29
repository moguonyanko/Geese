package org.geese.ci.classifier.db.mongodb.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import org.geese.ci.classifier.Feature;
import org.geese.ci.classifier.db.StoreDef;
import org.geese.ci.classifier.db.StoreElementDef;
import org.geese.ci.classifier.db.dao.FeatureCountDao;
import org.geese.ci.classifier.db.mongodb.MongoDBConnection;

public class MongoDBFeatureCountDao implements FeatureCountDao {
	
	private final MongoDBConnection con;
	
	private static final String COLLECTION_NAME = StoreDef.FEATURECOUNT.getName();
	
	private static final String FEATURE = StoreElementDef.FEATURE.getName();
	private static final String CATEGORY = StoreElementDef.CATEGORY.getName();
	private static final String COUNT = StoreElementDef.COUNT.getName();

	public MongoDBFeatureCountDao(MongoDBConnection con) {
		this.con = con;
	}

	@Override
	public boolean insert(Feature feature) {
		DBCollection dbColl = getDBCollection();

		DBObject newValueObj = new BasicDBObject();
		newValueObj.put(MongoDBDaoUtil.getObjectIdName(), MongoDBDaoUtil.getObjectId(feature));
		newValueObj.put(FEATURE, feature.getWord());
		newValueObj.put(CATEGORY, feature.getCategoryName());
		newValueObj.put(COUNT, 1.0);

		WriteResult result = dbColl.insert(newValueObj);
		CommandResult cres = result.getLastError();
		boolean ok = cres.ok();

		return ok;
	}

	@Override
	public double select(Feature feature) {
		DBCollection dbColl = getDBCollection();
		DBObject keys = new BasicDBObject();
		keys.put(MongoDBDaoUtil.getObjectIdName(), MongoDBDaoUtil.getObjectId(feature));
		DBObject valueObj = dbColl.findOne(keys);
		if (valueObj != null) {
			Double count = (Double) valueObj.get(COUNT);
			return count;
		} else {
			return 0;
		}
	}

	@Override
	public int update(double count, Feature feature) {
		DBCollection dbColl = getDBCollection();

		DBObject keyObj = new BasicDBObject(MongoDBDaoUtil.getObjectIdName(), MongoDBDaoUtil.getObjectId(feature));
		
		DBObject valueObj = new BasicDBObject();
		valueObj.put(FEATURE, feature.getWord());
		valueObj.put(CATEGORY, feature.getCategoryName());
		valueObj.put(COUNT, count);

		WriteResult result = dbColl.update(keyObj, valueObj);
		CommandResult cres = result.getLastError();
		boolean ok = cres.ok();

		return ok ? 1 : 0;
	}

	private DBCollection getDBCollection() {
		DB db = con.getDB();
		DBCollection coll = db.getCollection(COLLECTION_NAME);
		return coll;
	}
}
