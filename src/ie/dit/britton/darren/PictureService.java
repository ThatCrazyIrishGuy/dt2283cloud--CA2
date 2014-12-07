package ie.dit.britton.darren;

import java.util.ArrayList;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.labs.repackaged.com.google.common.collect.ArrayListMultimap;
import com.google.appengine.labs.repackaged.com.google.common.collect.Multimap;

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