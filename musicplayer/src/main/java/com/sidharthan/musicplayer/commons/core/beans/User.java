package com.sidharthan.musicplayer.commons.core.beans;

/**
 * The Class User bean for User object.
 * 
 * @author sidharthan.r
 */
public class User extends BaseBean {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2763115640669380878L;

	/** The email id. */
	private String emailId;
	
	/** The password. */
	private String password;
	
	/** The last played song. */
	private Song lastPlayedSong;

	/**
	 * Gets the last played song.
	 *
	 * @return the last played song
	 */
	public Song getLastPlayedSong() {
		return lastPlayedSong;
	}

	/**
	 * Sets the last played song.
	 *
	 * @param lastPlayedSong the new last played song
	 */
	public void setLastPlayedSong(Song lastPlayedSong) {
		this.lastPlayedSong = lastPlayedSong;
	}

	/**
	 * Gets the email id.
	 *
	 * @return the email id
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Sets the email id.
	 *
	 * @param emailId the new email id
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
