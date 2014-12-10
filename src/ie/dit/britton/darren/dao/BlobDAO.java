package ie.dit.britton.darren.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * BlobDAO.java - provides methods for writing and reading picinfo entities to the datastore.
 * 
 * @author Darren Britton
 * @see DatastoreService
 */
public class BlobDAO
{

	DatastoreService datastore;

	public BlobDAO()
	{
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	/**
	 * Puts picinfo into the datastore for provided blobKey.
	 * 
	 * @param blobKey
	 *            A list of BlobKeys.
	 */
	public void uploadInfo(List<BlobKey> blobKey)
	{
		UserService userService = UserServiceFactory.getUserService(); // gets instance of user service instance
		String name = userService.getCurrentUser().getNickname(); // gets user nickname
		boolean isPublic = !userService.isUserAdmin(); // if user is not admin, image is public

		Entity picInfo;

		for (BlobKey key : blobKey)
		{
			picInfo = new Entity("picinfo", key.getKeyString()); // uses key string as name index for entity
			picInfo.setProperty("uploader", name);
			picInfo.setProperty("public", isPublic);
			datastore.put(picInfo); // inserts new entity into the datastore
		}
	}

	/**
	 * Retrieves a list of all the current users blob keys as Strings.
	 * 
	 * @return A List of Strings.
	 */
	public List<String> getUserBlobs()
	{
		ArrayList<String> blobs = new ArrayList<String>();

		UserService userService = UserServiceFactory.getUserService(); // gets instance of user service instance
		String uploader = userService.getCurrentUser().getNickname();

		String blobKey = "";

		Filter nameFilter = new FilterPredicate("uploader",
				FilterOperator.EQUAL, uploader); // gets all entites where uploader is current user

		// Uses the Query class to assemble a query
		Query q = new Query("picinfo").setFilter(nameFilter);

		// Uses the PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) // itterates over all recieved entities
		{
			blobKey = result.getKey().getName(); //gets the entities name index
			blobs.add(blobKey); // adds name index (blobkey as string) to list 
		}

		return blobs;
	}

	/**
	 * Retrieves a list of all the blob keys, as Strings, marked public in the datastore.
	 * 
	 * @return A List of Strings.
	 */
	public List<String> getPublicBlobs()
	{
		ArrayList<String> blobs = new ArrayList<String>();

		String blobKey = "";

		Filter visibilityFilter = new FilterPredicate("public",
				FilterOperator.EQUAL, true);  // gets all entites that are public

		// Use class Query to assemble a query
		Query q = new Query("picinfo").setFilter(visibilityFilter);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) // itterates over all recieved entities
		{
			blobKey = result.getKey().getName();  //gets the entities name index
			blobs.add(blobKey); // adds name index (blobkey as string) to list 
		}

		return blobs;
	}

	/**
	 * Retrieves a list of all blob keys as Strings.
	 * 
	 * @return A List of Strings.
	 */
	public List<String> getAllBlobs()
	{
		ArrayList<String> blobs = new ArrayList<String>();

		String blobKey = "";
		
		// Use class Query to assemble a query
		Query q = new Query("picinfo");

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) // itterates over all recieved entities
		{
			blobKey = result.getKey().getName();  //gets the entities name index
			blobs.add(blobKey); // adds name index (blobkey as string) to list 
		}

		return blobs;
	}

	/**
	 * deletes the picinfo entity associated with a given BlobKey String.
	 * 
	 * @param blobKey
	 *            a BlobKey as String.
	 */
	public void deleteBlob(String blobKey)
	{
		Key key = KeyFactory.createKey("picinfo", blobKey); //converts key string into key object
		datastore.delete(key); // deletes entity with given key from the datastore
	}

	/**
	 * gets the visibility of the picinfo entity associated with a given BlobKey String.
	 * 
	 * @param blobKey
	 *            a BlobKey as String.
	 * @return String of value "Private" or "Public".
	 */
	public String getBlobVisibility(String blobKey)
	{
		Entity entity;
		Key key = KeyFactory.createKey("picinfo", blobKey); //converts key string into key object

		try
		{
			entity = datastore.get(key); //gets entity from the datastore with given key
			
		} 
		catch (EntityNotFoundException e)
		{
			e.printStackTrace();
			return "error";
		}

		return (boolean) entity.getProperty
				("public") ? "Private" : "Public"; //returns Private if false and Public if True
	}

	/**
	 * gets the uploader a given BlobKey String.
	 * 
	 * @param blobKey
	 *            a BlobKey as String.
	 * @return String of uploaders nickname.
	 */
	public String getBlobOwner(String blobKey)
	{
		Entity entity;
		Key key = KeyFactory.createKey("picinfo", blobKey); //converts key string into key object

		try
		{
			entity = datastore.get(key); //gets entity from the datastore with given key
			
		} 
		catch (EntityNotFoundException e)
		{
			e.printStackTrace();
			return "error";
		}

		return (String) entity.getProperty("uploader"); //returns uploader nickname
	}

	/**
	 * inverts the visibility of the picinfo entity associated with a given BlobKey String.
	 * 
	 * @param blobKey
	 *            a BlobKey as String.
	 */
	public void updateBlobVisibility(String blobKey)
	{
		Entity entity;
		Key key = KeyFactory.createKey("picinfo", blobKey); //converts key string into key object

		try
		{
			entity = datastore.get(key); //gets entity from the datastore with given key
			
		} 
		catch (EntityNotFoundException e)
		{
			e.printStackTrace();
			return;
		}

		boolean isPublic = (boolean) entity.getProperty("public"); //gets visibility of entity

		entity.setProperty("public", !isPublic); // inverts the visibility

		datastore.put(entity); //puts the updated entity in the datastore (overwriting the old)
	}
}