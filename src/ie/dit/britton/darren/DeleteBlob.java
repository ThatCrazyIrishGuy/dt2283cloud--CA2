package ie.dit.britton.darren;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class DeleteBlob extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		String key = (String) req.getParameter("blobKey");
		BlobKey blobKey = new BlobKey(key);

		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		blobstoreService.delete(blobKey);

		BlobDAO blobDAO = new BlobDAO();
		blobDAO.deleteBlob(key);

		resp.sendRedirect("/getuserblobs");
	}
}