package com.sidharthan.musicplayer.commons.core.beans;

/**
 * The Class Song bean for song object.
 * 
 * @author sidharthan.r
 */
public class Song extends BaseBean {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7287617324151991933L;

	/** The title. */
	private String title;
	
	/** The album. */
	private String album;
	
	/** The rating. */
	private double rating;
	
	/** The src. */
	private String src;
	
	/** The album art. */
	private String albumArt;
	
	/** The duration. */
	private double duration;

	/**
	 * Gets the src.
	 *
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * Sets the src.
	 *
	 * @param src the new src
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * Gets the album art.
	 *
	 * @return the album art
	 */
	public String getAlbumArt() {
		return albumArt;
	}

	/**
	 * Sets the album art.
	 *
	 * @param albumArt the new album art
	 */
	public void setAlbumArt(String albumArt) {
		this.albumArt = albumArt;
	}


	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the album.
	 *
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * Sets the album.
	 *
	 * @param album the new album
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * Sets the rating.
	 *
	 * @param rating the new rating
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

}
