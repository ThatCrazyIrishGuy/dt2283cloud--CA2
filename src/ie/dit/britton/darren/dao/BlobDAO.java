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
* @author  Darren Britton
* @see DatastoreService
*/
public class BlobDAO {

	DatastoreService datastore;

	public BlobDAO() {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	/** 
    * Puts picinfo into the datastore for provided blobKey. 
    * @param blobKey A list of BlobKeys. 
    */ 
	public void uploadInfo(List < BlobKey > blobKey) {
		UserService userService = UserServiceFactory.getUserService(); //gets instance of user service instance
		String name = userService.getCurrentUser().getNickname();
		boolean isPublic = userService.isUserAdmin();

		Entity picInfo;

		for (BlobKey key: blobKey) {
			picInfo = new Entity("picinfo", key.getKeyString());
			picInfo.setProperty("uploader", name);
			picInfo.setProperty("public", isPublic);
			datastore.put(picInfo);
		}
	}
	
	/** 
    * Retrieves a list of all the current users blob keys as Strings. 
    * @return A List of Strings. 
    */ 
	public List < String > getUserBlobs() {
		ArrayList < String > blobs = new ArrayList < String > ();

		UserService userService = UserServiceFactory.getUserService(); //gets instance of user service instance
		String uploader = userService.getCurrentUser().getNickname();

		String blobKey = "";

		Filter nameFilter = new FilterPredicate("uploader",
		FilterOperator.EQUAL,
		uploader);


		// Use class Query to assemble a query
		Query q = new Query("picinfo").setFilter(nameFilter);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);


		for (Entity result: pq.asIterable()) {
			blobKey = result.getKey().getName();
			blobs.add(blobKey);
		}


		return blobs;
	}
	
	/** 
    * Retrieves a list of all the blob keys, as Strings, marked public in the datastore. 
    * @return A List of Strings. 
    */ 
	public List < String > getPublicBlobs() {
		ArrayList < String > blobs = new ArrayList < String > ();

		String blobKey = "";

		Filter visibilityFilter = new FilterPredicate("public",
		FilterOperator.EQUAL,
		true);


		// Use class Query to assemble a query
		Query q = new Query("picinfo").setFilter(visibilityFilter);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);


		for (Entity result: pq.asIterable()) {
			blobKey = result.getKey().getName();
			blobs.add(blobKey);
		}


		return blobs;
	}
	
	/** 
    * Retrieves a list of all blob keys as Strings. 
    * @return A List of Strings. 
    */ 
	public List < String > getAllBlobs() {
		ArrayList < String > blobs = new ArrayList < String > ();

		String blobKey = "";

		Query q = new Query("picinfo");

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result: pq.asIterable()) {
			blobKey = result.getKey().getName();
			blobs.add(blobKey);
		}


		return blobs;
	}
	
	/** 
    * deletes the picinfo entity associated with a given BlobKey String. 
    * @param blobKey a BlobKey as String. 
    */ 
	public void deleteBlob(String blobKey) {
		Key key = KeyFactory.createKey("picinfo", blobKey);
		datastore.delete(key);
	}
	
	/** 
    * gets the visibility of the picinfo entity associated with a given BlobKey String. 
    * @param blobKey a BlobKey as String. 
    * @return String of value "Private" or "Public". 
    */ 
	public String getBlobVisibility(String blobKey) {
		Entity entity;

		try {
			entity = datastore.get(KeyFactory.createKey("picinfo", blobKey));
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}

		return (boolean) entity.getProperty("public") ? "Private" : "Public";
	}
	
	/** 
    * gets the uploader a given BlobKey String. 
    * @param blobKey a BlobKey as String. 
    * @return String of uploaders nickname. 
    */ 
	public String getBlobOwner(String blobKey) {
		Entity entity;

		try {
			entity = datastore.get(KeyFactory.createKey("picinfo", blobKey));
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}

		return (String) entity.getProperty("uploader");
	}
	
	/** 
    * inverts the visibility of the picinfo entity associated with a given BlobKey String. 
    * @param blobKey a BlobKey as String. 
    */ 
	public void updateBlobVisibility(String blobKey) {
		Entity entity;

		try {
			entity = datastore.get(KeyFactory.createKey("picinfo", blobKey));
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		boolean isPublic = (boolean) entity.getProperty("public");

		entity.setProperty("public", !isPublic);

		datastore.put(entity);
	}
}