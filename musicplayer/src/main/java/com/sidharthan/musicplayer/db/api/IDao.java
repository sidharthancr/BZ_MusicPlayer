package com.sidharthan.musicplayer.db.api;

import java.util.List;

import com.mongodb.DBObject;

/**
 * Base interface for any database model operations.
 * 
 * @param <T>
 *            the generic type
 * @author sidharthan.r
 */

public interface IDao<T> {

	/**
	 * Store the list of model object into an persisted store.
	 * 
	 * @param objectList
	 *            the object list
	 * @param schemaName
	 *            DB Schema Name.
	 * @throws Exception 
	 */
	void add(List<T> objectList, String schemaName) throws Exception;

	/**
	 * Store the model object into an persisted store.
	 * 
	 * @param object
	 *            object to be added.
	 * @param schemaName
	 *            DB Schema Name.
	 * 
	 * @return objectId.
	 * @throws Exception 
	 */
	String add(T object, String schemaName) throws Exception;

	/**
	 * Save the model object into an persisted store.
	 * 
	 * @param object
	 *            object to be added.
	 * @param schemaName
	 *            DB Schema Name.
	 * 
	 * @return objectId.
	 * @throws Exception 
	 */
	String save(T object, String schemaName) throws Exception;

	/**
	 * Update any parameter in the model into persisted store.
	 * 
	 * @param object
	 *            object to be updated.
	 * @param schemaName
	 *            DB Schema Name.
	 */
	void update(T object, String schemaName);

	/**
	 * Update any parameter in the model into persisted store based on object
	 * id.
	 * 
	 * @param object
	 *            object to be updated.
	 * @param schemaName
	 *            DB Schema Name.
	 * @throws Exception 
	 */
	void updateWithObjectId(T object, String schemaName) throws Exception;

	/**
	 * Delete the model from persisted store.
	 * 
	 * @param object
	 *            object to be deleted.
	 * @param schemaName
	 *            DB Schema Name.
	 */
	void delete(T object, String schemaName);

	/**
	 * Get the model from persisted store by the given unique identifier.
	 * 
	 * @param id
	 *            id to be retrieve.
	 * @param schemaName
	 *            DB Schema Name.
	 * @return T object.
	 */
	T getById(String id, String schemaName);

	/**
	 * Get the model from persisted store by the given unique object identifier.
	 * 
	 * @param id
	 *            id to be retrieve.
	 * @param schemaName
	 *            DB Schema Name.
	 * @return T object.
	 */
	T getByObjectId(String id, String schemaName);

	/**
	 * List of unique ids that needs to be removed from persistent store.
	 * 
	 * @param ids
	 *            unique ids
	 * @param schemaName
	 *            DB Schema Name.s
	 * 
	 **/
	void delete(String[] ids, String schemaName);

	/**
	 * Returns total records of the given collection/table.
	 * 
	 * @param schemaName
	 *            table or collection name.
	 * @return total records count.
	 * @throws Exception 
	 * 
	 */
	long getTotalRecords(String schemaName) throws Exception;

	/**
	 * Implement this method to get the Mongo DB object instance from domain
	 * model instance. To insert the model into NoSql database ie, Mongo.
	 * 
	 * @param object
	 *            <T> - instance of an domain model
	 * @return DBObject object.
	 */
	DBObject getDBObject(T object);

	/**
	 * Implement this method to get domain model instance from Mongo DB
	 * <code> DBObject </code> instance.
	 * 
	 * @param dbObject
	 *            - MongoDB fills NoSql data.
	 * @return <T> - Returns domain model.
	 */
	T getModelObject(DBObject dbObject);

	/**
	 * Gets the updated db object.
	 * 
	 * @param object
	 *            T Object.
	 * @param updatableObject
	 *            Updatable Object.
	 * @return DBObject
	 */

	DBObject getUpdatedDBObject(T object, DBObject updatableObject);
}
