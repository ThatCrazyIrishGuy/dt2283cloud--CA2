package ie.dit.britton.darren;

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

public class BlobDAO {

	DatastoreService datastore;

	public BlobDAO() {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

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

	public void deleteBlob(String blobKey) {
		Key key = KeyFactory.createKey("picinfo", blobKey);
		datastore.delete(key);
	}

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