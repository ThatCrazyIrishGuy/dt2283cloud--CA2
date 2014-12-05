package ie.dit.britton.darren;

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

public class Upload extends HttpServlet {
	/**
* 
*/
	private static final long serialVersionUID = 1L;
	private BlobDAO blobDAO;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKey = blobs.get("myFile");
		if (blobKey == null) {
			res.sendRedirect("/");
		} else {
			
			blobDAO = new BlobDAO();
			blobDAO.uploadInfo(blobKey);
			res.sendRedirect("/getblobs");
		}
	}
}