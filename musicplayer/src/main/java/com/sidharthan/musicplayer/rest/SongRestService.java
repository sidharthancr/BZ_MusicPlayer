package com.sidharthan.musicplayer.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

import com.sidharthan.musicplayer.commons.constants.MusicPlayerConstants;
import com.sidharthan.musicplayer.management.SongManagement;

/**
 * The Class SongRestService for song rest calls.
 * 	
 * @author sidharthan.r
 */
@Path("/song")
public class SongRestService {

	/** The Constant songManagement. */
	private final static SongManagement songManagement = new SongManagement();
	/** The Constant LOGGER. */
	final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserRestService.class);

	/**
	 * Gets the songs list.
	 *
	 * @param sortBy
	 *            the sort by
	 * @return the songs list
	 */
	@GET
	@Path("/getSongsList")
	public Response getSongsList(@QueryParam("sortBy") String sortBy) {
		LOGGER.trace("Starting updateLastPlayedSong method with values: sortBy:"+sortBy);
		String result = "";
		final ObjectMapper mapper = new ObjectMapper();
		try {
			if (sortBy == null) {
				sortBy = MusicPlayerConstants.TITTLE;
			}
			final  Map<String, Object> map = new HashMap<String, Object>();
			map.put(MusicPlayerConstants.RETURN_DATA, songManagement.getSongsList(sortBy));
			result = mapper.writeValueAsString(map);
		} catch (Exception e) {
			LOGGER.error("Exception while getSongsList.",e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		LOGGER.trace("Exiting getSongsList method with reponse."+result);
		return Response.ok(result).build();

	}
}
