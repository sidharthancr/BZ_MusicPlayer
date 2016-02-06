package com.sidharthan.musicplayer.management;

import com.sidharthan.musicplayer.commons.core.beans.Song;
import com.sidharthan.musicplayer.db.UserDao;

/**
 * The Class UserManagement layer for apply business logics.
 * 
 * @author sidharthan.r
 */
public class UserManagement {

	private final static UserDao userDao = new UserDao();

	/**
	 * Login user with given credentials.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String login(String userName, String password) throws Exception {
		return userDao.login(userName, password).toString();
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
		userDao.updateLastPlayedSong(userId, song);
	}

	/**
	 * Gets the last played song.
	 *
	 * @param userId
	 *            the user id
	 * @return the last played song
	 */
	public Song getLastPlayedSong(String userId) {
		return userDao.getLastPlayedSong(userId);

	}
}
