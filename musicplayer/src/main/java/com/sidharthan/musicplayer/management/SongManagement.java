package com.sidharthan.musicplayer.management;

import java.util.List;

import com.sidharthan.musicplayer.commons.core.beans.Song;
import com.sidharthan.musicplayer.db.SongDao;

/**
 * The Class SongManagement to write business layer.
 */
public class SongManagement {

	/** The Constant songDao. */
	private final static SongDao songDao = new SongDao();

	/**
	 * Gets the songs list.
	 *
	 * @param sortBy
	 *            the sort by
	 * @return the songs list
	 */
	public List<Song> getSongsList(String sortBy) {
		return songDao.getSongsList(sortBy);
	}

}
