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
* PictureService.java - Encapsulates the retrevial of picinfo properties from the datastore. 
* @author  Darren Britton
* @see HttpServlet
* @See BlobDAO
* @see ImageService
*/
public class PictureService {

	private Multimap < String, String > picInfoMap;

	private BlobKey key;

	private String keyString;
	private String visibility;

	private BlobInfo blobInfo;
	private BlobDAO blobDAO;
	private BlobInfoFactory blobInfoFactory;
	private ArrayList < String > blobList;

	private ServingUrlOptions suo;
	private ImagesService imagesService;

	public PictureService(ArrayList < String > blobKeys) {
		picInfoMap = ArrayListMultimap.create();

		blobInfoFactory = new BlobInfoFactory();
		blobDAO = new BlobDAO();
		blobList = blobKeys;

		imagesService = ImagesServiceFactory.getImagesService();
	}
	
	/** 
    * Gets the filename and serving url for the loaded blobKeys. 
    * @return Multimap<String,String> containing the filename and serving url indexed by the BlobKey as a String. 
    */ 
	public Multimap < String, String > getCoreInfo() {
		for (int i = 0; i < blobList.size(); i++) {
			//resp.getWriter().println("blobs recieved =" + blobList.size() + "\n blobkey =" + blobList.get(i));
			keyString = blobList.get(i);
			key = new BlobKey(blobList.get(i));
			blobInfo = blobInfoFactory.loadBlobInfo(key);
			suo = ServingUrlOptions.Builder.withBlobKey(key);

			picInfoMap.put(keyString, blobInfo.getFilename());
			picInfoMap.put(keyString, imagesService.getServingUrl(suo));
		}

		return picInfoMap;
	}
	
	/** 
    * Gets the full file info for the loaded blobKeys. 
    * @return Multimap<String,String> containing
    * the serving url, filename, creation date, file type, file size and its visibility 
    * indexed by the BlobKey as a String. 
    */ 
	public Multimap < String, String > getFullInfo() {
		for (int i = 0; i < blobList.size(); i++) {
			//resp.getWriter().println("blobs recieved =" + blobList.size() + "\n blobkey =" + blobList.get(i));
			keyString = blobList.get(i);
			key = new BlobKey(keyString);
			suo = ServingUrlOptions.Builder.withBlobKey(key);
			blobInfo = blobInfoFactory.loadBlobInfo(key);
			visibility = blobDAO.getBlobVisibility(keyString);

			picInfoMap.put(keyString, imagesService.getServingUrl(suo));
			picInfoMap.put(keyString, blobInfo.getFilename());
			picInfoMap.put(keyString, blobInfo.getCreation().toString());
			picInfoMap.put(keyString, blobInfo.getContentType());
			picInfoMap.put(keyString, String.valueOf(blobInfo.getSize() / 1024));
			picInfoMap.put(keyString, visibility);
		}

		return picInfoMap;
	}
}