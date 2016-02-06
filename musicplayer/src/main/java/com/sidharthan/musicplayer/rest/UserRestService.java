package com.sidharthan.musicplayer.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

import com.sidharthan.musicplayer.commons.constants.MusicPlayerConstants;
import com.sidharthan.musicplayer.commons.core.beans.Song;
import com.sidharthan.musicplayer.management.UserManagement;

/**
 * The Class UserRestService for rest service to handle user operations.
 * 
 * 
 * @author sidharthan.r
 */
@Path("/user")
public class UserRestService {
	
	/** The Constant LOGGER. */
	final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserRestService.class);
	
	/** The Constant userManagement. */
	private final static UserManagement userManagement= new UserManagement();

	/**
	 * Login customer.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @return the response
	 */
	@POST
	@Path("/login")
	public Response login(@HeaderParam("userName") String userName, @HeaderParam("password") String password) {
		String result = "";
		LOGGER.info("Starting login mehtod with values: UserName:"+userName+" password:"+password);
		try {
			if (userName != null && password != null) {
				result = userManagement.login(userName, password);
			} else {
				throw new Exception("Inadequate Data");
			}
		} catch (Exception e) {
			LOGGER.error("Exception while login.",e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		LOGGER.info("Exiting login method with reponse."+result);
		return Response.ok(result).build();

	}

	/**
	 * Update last platyed song.
	 *
	 * @param userId
	 *            the user id
	 * @param jsonString
	 *            the json string
	 * @return the response
	 */
	@POST
	@Path("/updateLastPlayedSong")
	public Response updateLastPlayedSong(@HeaderParam("userId") String userId, String jsonString) {
		String result = "User updated successfully ";
		LOGGER.trace("Starting updateLastPlayedSong method with values: UserId:"+userId);
		final ObjectMapper mapper = new ObjectMapper();
		try {
			if (userId != null) {
				final Song song = mapper.readValue(jsonString, Song.class);
				userManagement.updateLastPlayedSong(userId, song);
			} else {
				throw new Exception("Inadequate Data");
			}
		} catch (Exception e) {
			LOGGER.error("Exception while updateLastPlayedSong.",e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		LOGGER.trace("Exiting updateLastPlayedSong method with reponse."+result);
		return Response.ok(result).build();

	}

	/**
	 * Update last played song.
	 *
	 * @param userId
	 *            the user id
	 * @return the response
	 */
	@GET
	@Path("/getLastPlayedSong")
	public Response getLastPlayedSong(@HeaderParam("userId") String userId) {
		String result = "";
		LOGGER.trace("Starting updateLastPlayedSong method with values: UserId:"+userId);
		final ObjectMapper mapper = new ObjectMapper();
		try {
			if (userId != null) {
				Song song = userManagement.getLastPlayedSong(userId);
				if (song != null) {
					final Map<String, Song> map = new HashMap<String, Song>();
					map.put(MusicPlayerConstants.RETURN_DATA, song);
					result = mapper.writeValueAsString(map);
				} else {
					throw new Exception("No last played Song Found");
				}
			} else {
				throw new Exception("Inadequate Data");
			}
		} catch (Exception e) {
			LOGGER.error("Exception while getLastPlayedSong.",e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		LOGGER.trace("Exiting getLastPlayedSong method with reponse."+result);
		return Response.ok(result).build();

	}

}
