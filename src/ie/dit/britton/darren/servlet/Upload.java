package ie.dit.britton.darren.servlet;

import ie.dit.britton.darren.dao.BlobDAO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * Upload.java - Servlet that that encapsulates the uploding of provided images to blobstore and adding associated info to the datastore via BlobDAO.
 * 
 * @author Darren Britton
 * @see HttpServlet
 * @See BlobDAO
 */
public class Upload extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		BlobDAO blobDAO;
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);// gets all the images from the http multipart request
		List<BlobKey> blobKey = blobs.get("myFile"); // gets the blobkeys for "myFile"
		if (blobKey == null)
		{ //front end ensures this cannot be null, just an extra precaution
			res.sendRedirect("/");
		}
		else
		{
			blobDAO = new BlobDAO();
			blobDAO.uploadInfo(blobKey); // creates the entities for list of blobkeys
			res.sendRedirect("/getblobs"); // redirects to landing page
		}
	}
}