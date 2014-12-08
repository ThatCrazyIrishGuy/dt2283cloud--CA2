package ie.dit.britton.darren.servlet;

import ie.dit.britton.darren.dao.BlobDAO;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** 
* DeleteBlob.java - a servlet that handles deleting a blob from the blobstore
* and its associated picinfo entity from the datastore. 
* @author  Darren Britton
* @see HttpServlet
*/
@SuppressWarnings("serial")
public class DeleteBlob extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		BlobDAO blobDAO = new BlobDAO();
		UserService userService = UserServiceFactory.getUserService();
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		
		String key = (String) req.getParameter("blobKey");
		String returnTo = (String) req.getParameter("returnTo");
		BlobKey blobKey = new BlobKey(key);
		
		if(blobDAO.getBlobOwner(key).equals(String.valueOf(userService.getCurrentUser())) || userService.isUserAdmin())
		{
			blobstoreService.delete(blobKey);
			blobDAO.deleteBlob(key);
		}

		resp.sendRedirect("/" + returnTo);
	}
}