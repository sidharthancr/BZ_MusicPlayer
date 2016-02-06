package com.sidharthan.musicplayer.db;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sidharthan.musicplayer.commons.constants.MusicPlayerConstants;
import com.sidharthan.musicplayer.commons.core.beans.Song;
import com.sidharthan.musicplayer.db.util.Dao;

/**
 * The Class SongDao to handle database operations
 * 
 * @author sidharthan.r
 */
public class SongDao extends Dao<Song> {

	/** The Constant SCHEMA_NAME. */
	final static String SCHEMA_NAME = "song";

	public Song getModelObject(DBObject dbObject) {
		if (dbObject == null) {
			return null;
		}
		final Song song = new Song();
		song.setAlbum(String.valueOf(dbObject.get(MusicPlayerConstants.ALBUM)));
		song.setAlbumArt(String.valueOf(dbObject.get(MusicPlayerConstants.ALBUM_ART)));
		if (dbObject.get(MusicPlayerConstants.RATING) != null) {
			song.setRating(Double.valueOf(String.valueOf(dbObject.get(MusicPlayerConstants.RATING))));
		}
		song.setTitle(String.valueOf(dbObject.get(MusicPlayerConstants.TITTLE)));
		song.setSrc(String.valueOf(dbObject.get(MusicPlayerConstants.SRC)));
		if (dbObject.get(MusicPlayerConstants.DURATION) != null) {
			song.setDuration(Double.parseDouble(String.valueOf(dbObject.get(MusicPlayerConstants.DURATION))));
		}
		song.setId(String.valueOf(dbObject.get(MusicPlayerConstants.ID_WITH_UNSERSCORE)));
		return song;
	}

	public DBObject getUpdatedDBObject(Song song, DBObject songDBObject) {
		if (song == null || songDBObject == null) {
			return songDBObject;
		}
		if (song.getId() != null) {
			songDBObject.put(MusicPlayerConstants.ID_WITH_UNSERSCORE, song.getId());
		}
		if (song.getAlbum() != null) {
			songDBObject.put(MusicPlayerConstants.ALBUM, song.getAlbum());
		}
		if (song.getAlbumArt() != null) {
			songDBObject.put(MusicPlayerConstants.ALBUM_ART, song.getAlbumArt());
		}
		songDBObject.put(MusicPlayerConstants.DURATION, song.getDuration());
		if (song.getSrc() != null) {
			songDBObject.put(MusicPlayerConstants.SRC, song.getSrc());
		}
		if (song.getTitle() != null) {
			songDBObject.put(MusicPlayerConstants.TITTLE, song.getTitle());
		}
		songDBObject.put(MusicPlayerConstants.RATING, song.getRating());
		return songDBObject;
	}

	/**
	 * Gets the songs list.
	 *
	 * @param sortBy
	 *            the sort by
	 * @return the songs list
	 */
	public List<Song> getSongsList(String sortBy) {
		final List<Song> listOfSong = new ArrayList<Song>();
		int sortOrder = 1;
		if (sortBy.equals(MusicPlayerConstants.RATING)) {
			sortOrder = -1;
		}
		final DBObject query = new BasicDBObject(sortBy, sortOrder);
		final DBCollection collection = getDb().getCollection(SCHEMA_NAME);
		final DBCursor result = collection.find().sort(query);
		while (result.hasNext()) {
			listOfSong.add(getModelObject(result.next()));
		}
		return listOfSong;
	}

}
