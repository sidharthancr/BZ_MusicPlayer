package com.sidharthan.musicplayer.db.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import com.sidharthan.musicplayer.commons.core.beans.BaseBean;
import com.sidharthan.musicplayer.db.api.IDao;

/**
 * Basic implementation of an Mongo DB operations.
 * 
 * @param <T>
 *            the generic type
 * @author sidharthan.r
 */

public abstract class Dao<T> implements IDao<T> {

	final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Dao.class);
	/**
	 * Variable to hold Database object.
	 */
	private DB db;
	/**
	 * Variable to hold database Name.
	 */
	private String dbName = "musicplayer";

	/**
	 * Gets the db.
	 * 
	 * @return the db
	 * @throws Exception
	 */
	public DB getDb() {
		if (db == null || !db.getName().equals(dbName)) {
			db = DatabaseUtil.getDb(dbName);
		}
		if (db == null) {
			try {
				throw new Exception("Database connection is empty.");
			} catch (Exception e) {
				LOGGER.error("Exception while getting DB",e);
			}
		}
		return db;
	}

	/**
	 * Setting the DB.
	 * 
	 * @param db
	 *            the new db
	 */
	public void setDb(DB db) {
		this.db = db;
	}

	/**
	 * Gets the db.
	 * 
	 * @param databaseName
	 *            the db name
	 * @return the db
	 * @throws Exception
	 */
	public DB getDb(String databaseName) {
		if (db == null || !db.getName().equals(this.dbName)) {
			return DatabaseUtil.getDb(databaseName);
		}
		return db;
	}

	/**
	 * Gets the aggregated stat db.
	 * 
	 * @param aggregatedStatDBName
	 *            holds the unique name of the aggregated stat.
	 * @return the aggregated stat db.
	 */
	public DB getAggregatedStatDb(String aggregatedStatDBName) {
		return DatabaseUtil.getDb(aggregatedStatDBName);
	}

	public String add(T object, String schemaName) throws Exception {

		final DBObject dbObject = getDBObject(object);
		final DBCollection collection = getDb().getCollection(schemaName);
		try {
			collection.insert(dbObject);
		} catch (Exception e) {
			throw e;
		}
		if (dbObject.get("_id") != null) {
			System.out.println(dbObject);
			return dbObject.get("_id").toString();
		}
		return null;

	}

	public String save(T object, String schemaName) throws Exception {

		final DBObject dbObject = getDBObject(object);
		final DBCollection collection = getDb().getCollection(schemaName);
		try {
			collection.save(dbObject);
		} catch (Exception e) {
			throw e;
		}
		return null;

	}

	public void add(List<T> objectList, String schemaName) throws Exception {
		final List<DBObject> dbObjectList = new ArrayList<DBObject>();
		if (objectList != null && objectList.size() > 0) {
			for (T object : objectList) {
				dbObjectList.add(getDBObject(object));
			}
		}
		final DBCollection collection = getDb().getCollection(schemaName);
		try {
			collection.insert(dbObjectList);
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(T object, String schemaName) {
		final DBObject query = new BasicDBObject();
		query.put("_id", ((BaseBean) object).getId());
		try {
			updateObjectWithQuery(query, object, schemaName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update with object id.
	 * 
	 * @param object
	 *            the object
	 * @param schemaName
	 *            the schema name
	 * @throws Exception
	 */
	public void updateWithObjectId(T object, String schemaName) throws Exception {
		final DBObject query = new BasicDBObject();
		query.put("_id", new ObjectId((String) ((BaseBean) object).getId()));
		updateObjectWithQuery(query, object, schemaName);
	}

	/**
	 * Update object with query.
	 * 
	 * @param query
	 *            the query
	 * @param object
	 *            the object
	 * @param schemaName
	 *            the schema name
	 * @throws Exception
	 */
	private void updateObjectWithQuery(DBObject query, T object, String schemaName) throws Exception {
		final DBCollection collection = getDb().getCollection(schemaName);
		final DBObject updatableObject = collection.findOne(query);
		if (updatableObject == null) {
			throw new Exception("Record not exisis");
		}
		final DBObject dbObject = getUpdatedDBObject(object, updatableObject);
		collection.update(query, dbObject);

	}

	/**
	 * Gets the dB object.
	 * 
	 * @param object
	 *            the object
	 * @return the dB object
	 */
	public DBObject getDBObject(T object) {
		DBObject dbObject = new BasicDBObject();
		dbObject = getUpdatedDBObject(object, dbObject);
		String id = ((BaseBean) object).getId();
		if (id != null && !id.isEmpty()) {
			dbObject.put("_id", new ObjectId(((BaseBean) object).getId()));
		}
		return dbObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.payoda.commons.db.util.IDao#delete(java.lang.Object,
	 * java.lang.String)
	 */
	public void delete(T object, String schemaName) {

		final String id = ((BaseBean) object).getId();

		final DBObject delete = new BasicDBObject();
		delete.put("_id", id);
		final DBCollection collection = getDb().getCollection(schemaName);
		collection.remove(delete);

	}

	public T getById(String id, String schemaName) {
		final BasicDBObject query = new BasicDBObject();
		query.put("_id", id);
		return getByQuery(query, schemaName);
	}

	/**
	 * Gets the by object id.
	 * 
	 * @param id
	 *            the id
	 * @param schemaName
	 *            the schema name
	 * @return the by object id
	 */
	public T getByObjectId(String id, String schemaName) {
		final BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		return getByQuery(query, schemaName);
	}

	/**
	 * Gets the by query.
	 * 
	 * @param query
	 *            the query
	 * @param schemaName
	 *            the schema name
	 * @return the by query
	 */
	private T getByQuery(BasicDBObject query, String schemaName) {
		final DBCollection collection = getDb().getCollection(schemaName);
		final DBObject dbObject = collection.findOne(query);
		return getModelObject(dbObject);
	}

	public void delete(String[] ids, String schemaName) {

		final DBCollection collection = getDb().getCollection(schemaName);
		final BasicDBObject removeObject = new BasicDBObject();
		removeObject.put("_id", new BasicDBObject("$in", ids));
		collection.remove(removeObject);

	}

	public long getTotalRecords(String schemaName) throws Exception {

		final DBCollection collection = getDb().getCollection(schemaName);
		if (collection == null) {
			throw new Exception("Collection not found");
		}
		return collection.count();

	}

	/**
	 * Gets the db name.
	 * 
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * Sets the db name.
	 * 
	 * @param dbName
	 *            the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * Gets the parameter as object.
	 * 
	 * @param data
	 *            DBObject to be mapped to bean
	 * @return object
	 */
	public Object getParameterASObject(DBObject data) {

		if (data instanceof BasicDBObject) {
			final Map<String, Object> returnMap = new HashMap<String, Object>();
			for (String key : data.keySet()) {
				if (data.get(key) instanceof BasicDBObject || data.get(key) instanceof BasicDBList) {
					returnMap.put(key, getParameterASObject((DBObject) data.get(key)));
				} else {
					returnMap.put(key, data.get(key));
				}
			}
			return returnMap;
		} else if (data instanceof BasicDBList) {
			final List<Object> returnMap = new ArrayList<Object>();
			for (Object indivObj : (BasicDBList) data) {
				if (indivObj instanceof BasicDBObject || indivObj instanceof BasicDBList) {
					returnMap.add(getParameterASObject((DBObject) indivObj));
				} else {
					returnMap.add((String) indivObj);
				}
			}
			return returnMap;
		}
		return null;
	}

	/**
	 * Gets the map as obj.
	 * 
	 * @param inputData
	 *            contains the map which has to be converted as dbobject
	 * @return DBobject
	 */
	@SuppressWarnings("unchecked")
	public DBObject getMapAsObj(Map<String, ? extends Object> inputData) {
		final DBObject dataObj = new BasicDBObject();
		final Map<String, ? extends Object> data = (Map<String, ? extends Object>) inputData;
		for (String key : data.keySet()) {
			if (data.get(key) instanceof String) {
				dataObj.put(key, data.get(key));
			} else if (data.get(key) instanceof Map) {
				final DBObject tempMap = getMapAsObj((Map<String, ? extends Object>) data.get(key));
				dataObj.put(key, tempMap);
			} else if (data.get(key) instanceof List) {
				dataObj.put(key, getListAsObj((List<Object>) data.get(key), key));
			}
		}
		return dataObj;
	}

	/**
	 * Gets the list as obj.
	 * 
	 * @param data
	 *            contains the list which has to be converted as dbobject
	 * @param key
	 *            the key value to be added
	 * @return list of DBobject
	 */
	@SuppressWarnings("unchecked")
	public List<? extends Object> getListAsObj(List<? extends Object> data, String key) {
		final List<DBObject> innerDBObj = new ArrayList<DBObject>();
		for (Object dataList : data) {
			if (dataList instanceof String) {
				return data;
			} else if (dataList instanceof Map) {
				innerDBObj.add(getMapAsObj((Map<String, ? extends Object>) dataList));
			}
		}
		return innerDBObj;
	}

}
