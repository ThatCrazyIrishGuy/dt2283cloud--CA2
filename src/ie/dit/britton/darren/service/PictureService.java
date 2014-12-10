package ie.dit.britton.darren.service;

import ie.dit.britton.darren.dao.BlobDAO;

import java.util.ArrayList;

import javax.servlet.http.HttpServlet;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.labs.repackaged.com.google.common.collect.ArrayListMultimap;
import com.google.appengine.labs.repackaged.com.google.common.collect.Multimap;

/**
 * PictureService.java - Encapsulates the retrevial of picinfo properties from the datastore
 * and the converting of BlobKeys into static serving urls.
 * 
 * @author Darren Britton
 * @see HttpServlet
 * @See BlobDAO
 * @see ImageService
 */
public class PictureService
{

	private Multimap<String, String> picInfoMap;

	private BlobKey key;

	private String keyString;
	private String visibility;

	private BlobInfo blobInfo;
	private BlobDAO blobDAO;
	private BlobInfoFactory blobInfoFactory;
	private ArrayList<String> blobList;

	private ServingUrlOptions suo;
	private ImagesService imagesService;

	public PictureService(ArrayList<String> blobKeys)
	{
		picInfoMap = ArrayListMultimap.create(); // initalizes the google guava Multimap "picInfoMap"

		blobInfoFactory = new BlobInfoFactory();
		blobDAO = new BlobDAO();
		blobList = blobKeys; // blobKeys contains keys as strings obtained from the BlobDAO

		imagesService = ImagesServiceFactory.getImagesService();
	}

	/**
	 * Gets the filename and serving url for the loaded blobKeys.
	 * 
	 * @return Multimap<String,String> containing the filename 
	 * and serving url indexed by the BlobKey as a String.
	 */
	public Multimap<String, String> getCoreInfo()
	{
		for (int i = 0; i < blobList.size(); i++)
		{ // for each recieved key
			keyString = blobList.get(i);
			key = new BlobKey(blobList.get(i)); // convert key string to BlobKey object
			blobInfo = blobInfoFactory.loadBlobInfo(key); // gets the BlobInfo for this BlobKey
			suo = ServingUrlOptions.Builder.withBlobKey(key); // sets suo to get current BlobKey

			picInfoMap.put(keyString, blobInfo.getFilename()); // puts image name into map
			picInfoMap.put(keyString, imagesService.getServingUrl(suo)); // puts the serving url into map
		}

		return picInfoMap;
	}

	/**
	 * Gets the full file info for the loaded blobKeys.
	 * 
	 * @return Multimap<String,String> containing the serving url, filename, creation date, 
	 * file type, file size and its visibility indexed by the BlobKey as a String.
	 */
	public Multimap<String, String> getFullInfo()
	{
		for (int i = 0; i < blobList.size(); i++)
		{ // for each recieved key
			keyString = blobList.get(i);
			key = new BlobKey(keyString); // convert key string to BlobKey object
			suo = ServingUrlOptions.Builder.withBlobKey(key); // sets suo to get current BlobKey
			blobInfo = blobInfoFactory.loadBlobInfo(key); // gets the BlobInfo for this BlobKey
			visibility = blobDAO.getBlobVisibility(keyString); // gets the visibility for given key

			picInfoMap.put(keyString, imagesService.getServingUrl(suo)); // puts the serving url into map
			picInfoMap.put(keyString, blobInfo.getFilename()); // puts image name into map
			picInfoMap.put(keyString, blobInfo.getCreation().toString()); // puts image upload date into map
			picInfoMap.put(keyString, blobInfo.getContentType()); // puts image type into map
			picInfoMap.put(keyString, String.valueOf(blobInfo.getSize() / 1024)); // puts image size (in KB) into map
			picInfoMap.put(keyString, visibility); // puts image visibility into map
		}

		return picInfoMap;
	}
}