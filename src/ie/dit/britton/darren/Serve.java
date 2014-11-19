package ie.dit.britton.darren;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.BlobstoreService;

public class Serve extends Upload {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// file Serve.java
	private BlobstoreService blobstoreService =BlobstoreServiceFactory.getBlobstoreService();
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	 throws IOException {
	 BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
	 blobstoreService.serve(blobKey, res);
	 }
}
