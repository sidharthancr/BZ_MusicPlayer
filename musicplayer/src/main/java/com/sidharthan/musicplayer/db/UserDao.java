package com.sidharthan.musicplayer.db;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.sidharthan.musicplayer.commons.constants.MusicPlayerConstants;
import com.sidharthan.musicplayer.commons.core.beans.Song;
import com.sidharthan.musicplayer.commons.core.beans.User;
import com.sidharthan.musicplayer.db.util.Dao;

/**
 * The Class UserDao to handle database operations of User.
 * @author sidharthan.r
 */
public class UserDao extends Dao<User> {

	/** The Constant SCHEMA_NAME. */
	final static String SCHEMA_NAME = "user";
	
	final static SongDao songDao= new SongDao();
	

	/**
	 * Login.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @return the JSON object
	 * @throws Exception
	 *             the exception
	 */
	public JSONObject login(String userName, String password) throws Exception {
		final DBObject query = new BasicDBObject();
		query.put(MusicPlayerConstants.NAME, userName);
		query.put(MusicPlayerConstants.PASSWORD, password);
		final DBCollection collection = getDb().getCollection(SCHEMA_NAME);
		final DBObject result = collection.findOne(query);
		if (result != null) {
			final JSONObject returnData = new JSONObject();
			returnData.put(MusicPlayerConstants.MESSAGE, "User Authenticated Successfully");
			returnData.put(MusicPlayerConstants.USER_ID,
					result.get(MusicPlayerConstants.ID_WITH_UNSERSCORE).toString());
			final JSONObject resultJson = new JSONObject();
			resultJson.put(MusicPlayerConstants.RETURN_DATA, returnData);
			return resultJson;
		} else {
			throw new Exception("Invalid User credentials");
		}
	}

	public User getModelObject(DBObject dbObject) {
		if (dbObject == null) {
			return null;
		}
		final User user = new User();
		user.setId(String.valueOf(dbObject.get(MusicPlayerConstants.ID_WITH_UNSERSCORE)));
		user.setName(String.valueOf(dbObject.get(MusicPlayerConstants.NAME)));
		if (dbObject.get(MusicPlayerConstants.PASSWORD) != null) {
			user.setPassword(dbObject.get(MusicPlayerConstants.PASSWORD).toString());
		}
		if (dbObject.get(MusicPlayerConstants.MAIL_ID) != null) {
			user.setEmailId(dbObject.get(MusicPlayerConstants.MAIL_ID).toString());
		}
		if (dbObject.get(MusicPlayerConstants.LAST_PLAYED_SONG) != null) {
			DBObject songDBObject = (DBObject) dbObject.get(MusicPlayerConstants.LAST_PLAYED_SONG);
			user.setLastPlayedSong(songDao.getModelObject(songDBObject));
		}

		return user;
	}

	public DBObject getUpdatedDBObject(User object, DBObject updatableObject) {
		if (object == null || updatableObject == null) {
			return updatableObject;
		}
		if (object.getName() != null) {
			updatableObject.put(MusicPlayerConstants.NAME, object.getName());
		}

		if (object.getPassword() != null) {
			updatableObject.put(MusicPlayerConstants.PASSWORD, object.getPassword());
		}
		if (object.getEmailId() != null) {
			updatableObject.put(MusicPlayerConstants.MAIL_ID, object.getEmailId());
		}

		if (object.getLastPlayedSong() != null) {
			Song song = object.getLastPlayedSong();
			DBObject songDBObject = new BasicDBObject();
			updatableObject.put(MusicPlayerConstants.LAST_PLAYED_SONG, songDao.getUpdatedDBObject(song, songDBObject));
		}
		return updatableObject;
	}

	/**
	 * Update last played song.
	 *
	 * @param userId
	 *            the user id
	 * @param song
	 *            the song
	 * @throws Exception
	 *             the exception
	 */
	public void updateLastPlayedSong(String userId, Song song) throws Exception {
		final User user = new User();
		final double duration = song.getDuration();
		song=songDao.getByObjectId(song.getId(), MusicPlayerConstants.SONG);
		song.setDuration(duration);
		user.setLastPlayedSong(song);
		user.setId(userId);
		updateWithObjectId(user, SCHEMA_NAME);
	}

	/**
	 * Gets the last played song.
	 *
	 * @param userId
	 *            the user id
	 * @return the last played song
	 */
	public Song getLastPlayedSong(String userId) {
		final User user = getByObjectId(userId, SCHEMA_NAME);
		return user.getLastPlayedSong();

	}

}
