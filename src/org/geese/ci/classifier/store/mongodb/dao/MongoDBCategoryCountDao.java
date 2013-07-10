package org.geese.ci.classifier.store.mongodb.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import org.geese.ci.classifier.Category;
import org.geese.ci.classifier.store.StoreDef;
import org.geese.ci.classifier.store.StoreElementDef;
import org.geese.ci.classifier.store.dao.CategoryCountDao;
import org.geese.ci.classifier.store.mongodb.MongoDBConnection;

public class MongoDBCategoryCountDao implements CategoryCountDao{

	private final MongoDBConnection con;
	
	private static final String COLLECTION_NAME = StoreDef.CATEGORYCOUNT.getName();

	private static final String CATEGORY = StoreElementDef.CATEGORY.getName();
	private static final String COUNT = StoreElementDef.COUNT.getName();
	
	public MongoDBCategoryCountDao(MongoDBConnection con) {
		this.con = con;
	}
	
	@Override
	public boolean insert(Category category){
		DBCollection dbColl = getDBCollection();

		DBObject newValueObj = new BasicDBObject();
		newValueObj.put(MongoDBDaoUtil.getObjectIdName(), MongoDBDaoUtil.getObjectId(category));
		newValueObj.put(CATEGORY, category.getName());
		newValueObj.put(COUNT, 1.0);

		WriteResult result = dbColl.insert(newValueObj);
		CommandResult cres = result.getLastError();
		boolean ok = cres.ok();

		return ok;
	}

	@Override
	public double select(Category category){
		DBCollection dbColl = getDBCollection();

		DBObject dbObj = dbColl.findOne(new BasicDBObject(MongoDBDaoUtil.getObjectIdName(),
			MongoDBDaoUtil.getObjectId(category)));
		if(dbObj != null){
			Double count = (Double)dbObj.get(COUNT);
			return count;
		}else{
			return 0;
		}
	}

	@Override
	public Set<String> findAllCategories(){
		Set<String> categories = new HashSet<>();

		DBCollection dbColl = getDBCollection();
		try(DBCursor cursor = dbColl.find()){
			if(cursor.hasNext()){
				DBObject obj = cursor.next();
				String category = (String)obj.get(CATEGORY);
				categories.add(category);
			}
		}

		return categories;
	}

	@Override
	public List<Double> findAllCounts(){
		List<Double> counts = new ArrayList<>();

		DBCollection dbColl = getDBCollection();
		try(DBCursor cursor = dbColl.find()){
			if(cursor.hasNext()){
				DBObject obj = cursor.next();
				Double count = (Double)obj.get(COUNT);
				counts.add(count);
			}
		}

		return counts;
	}

	@Override
	public int update(double count, Category category){
		DBCollection dbColl = getDBCollection();

		DBObject keyObj = new BasicDBObject();
		keyObj.put(MongoDBDaoUtil.getObjectIdName(), MongoDBDaoUtil.getObjectId(category));

		DBObject valueObj = new BasicDBObject();
		valueObj.put(CATEGORY, category.getName());
		valueObj.put(COUNT, count);

		WriteResult result = dbColl.update(keyObj, valueObj);
		CommandResult cres = result.getLastError();
		boolean ok = cres.ok();

		return ok ? 1 : 0;
	}

	private DBCollection getDBCollection(){
		DB db = con.getDB();
		DBCollection coll = db.getCollection(COLLECTION_NAME);
		return coll;
	}
}
