package ie.dit.britton.darren;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.repackaged.com.google.api.client.util.Lists;
import com.sun.java.util.jar.pack.Package.File;

public class Upload extends HttpServlet {
/**
* 
*/
private static final long serialVersionUID = 1L;
private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
public void doPost(HttpServletRequest req, HttpServletResponse res)
 throws ServletException, IOException {
 @SuppressWarnings("deprecation")
 File myfile =(File) req.getAttribute("myFile");
Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
 List<BlobKey> blobKey = blobs.get("myFile");
 if (blobKey == null) {
 res.sendRedirect("/");
 } else {
	 
		String key;
		
		// iterator to store queried blob info
		List<BlobInfo> filesList = Lists.newArrayList(new BlobInfoFactory().queryBlobInfos());
				
		// loop through files information returned from query
		for(BlobInfo blobInfo : filesList)
		{	
			// retrieves blob key as a string with added characters which are not required
			key = blobInfo.getBlobKey().toString();
			
			// extract the key from returned key string
			key = key.substring(10, key.length()-1);
			
			// display a row with blob filename and info inside the html table
			res.getWriter().println("<tr><td>"+filesList.get(i).getFilename()+"</td><td>"+filesList.get(i).getContentType()+"</td><td>"+filesList.get(i).getSize()+"</td><td><input onClick=\"location.href='/serve?blob-key="+key+"'\" type=\"button\" value=\"Download\" /></td></tr>");
		}

 //System.out.println("Uploaded a file with blobKey:"+blobKey.getKeyString());
 //res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
 }
}
}