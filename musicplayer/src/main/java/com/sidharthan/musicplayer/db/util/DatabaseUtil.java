package com.sidharthan.musicplayer.db.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

/**
 * The Class DatabaseUtil to create mongoDB client.
 * 
 * @author sidharthan.r
 */
public final class DatabaseUtil {
	
	final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DatabaseUtil.class);

	/**
	 * Utility class should have private constructor so that other classes
	 * wouldn't be able to extend it.
	 */
	private DatabaseUtil() {
	}

	/** The mongo client. */
	private static MongoClient mongoClient;

	// Stores the locally cached mongo DB object instances for the given name
	/** The stored db. */
	private static HashMap<String, DB> storedDb;

	/**
	 * Gets the db.
	 * 
	 * @param name
	 *            the name
	 * @return the db
	 */
	public static DB getDb(String name) {

		if (mongoClient == null) {
			mongoClient = getMongo();
		}

		if (storedDb == null) {
			storedDb = new HashMap<String, DB>();
		}

		// Fetch the db object from the singleton map
		DB db = storedDb.get(name);

		// If object is available in the map return it direct - else create a
		// new instance and return
		if (db == null) {
			db = mongoClient.getDB(name);
			storedDb.put(name, db);
		}
		return db;
	}

	/**
	 * Gets the mongo.
	 * 
	 * @return the mongo
	 */
	private static MongoClient getMongo() {
		try {
			final List<ServerAddress> serverList = new ArrayList<ServerAddress>();
			final String dbHosts = "localhost:27017";

			if (dbHosts == null || dbHosts.isEmpty()) {
				// LOGGER.error("Missing database hosts value in the property. No db instance created.");
				return null;
			}
			final String hosts[] = dbHosts.split(",");

			for (int i = 0; i < hosts.length; i++) {
				int port;
				String ip;

				final String aDbHost = hosts[i];

				if (aDbHost != null) {
					final String[] hostInfo = aDbHost.split(":");
					if (hostInfo.length > 1) {
						ip = hostInfo[0];
						port = Integer.parseInt(hostInfo[1]);
						final ServerAddress aAddress = new ServerAddress(ip, port);
						serverList.add(aAddress);
					}
				}
			}
			// Fetch the Max Connection from the property files
			final int maxConnection = 100;
			// Set the mongoOptions
			final MongoClientOptions.Builder mongoClientOptionsBuilder = new MongoClientOptions.Builder();
			mongoClientOptionsBuilder.connectionsPerHost(maxConnection);
			mongoClientOptionsBuilder.connectTimeout(5000);
			final int timeOut = 300000;
			mongoClientOptionsBuilder.socketTimeout(timeOut);
			final MongoClientOptions mongoClientOptions = mongoClientOptionsBuilder.build();
			mongoClient = new MongoClient(serverList, mongoClientOptions);
		} catch (Exception e) {
			 LOGGER.error("Error while Creating mongo client:", e);
		}
		return mongoClient;
	}

}
