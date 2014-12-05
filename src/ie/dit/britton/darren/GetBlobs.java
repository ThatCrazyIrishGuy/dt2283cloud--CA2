package ie.dit.britton.darren;

import java.io.IOException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class GetBlobs extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
			  ArrayList<String> fileURLs = new ArrayList<String>();
	          ArrayList<String> blobNames = new ArrayList<String>();
	          BlobKey key;
	          // iterator to store queried blob info
	          BlobDAO blobDAO = new BlobDAO();
	          BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
	          UserService userService = UserServiceFactory.getUserService(); //gets instance of user service instance
	
	          ArrayList<String> blobList;
	
	          if (userService.isUserLoggedIn())
	          {
	              blobList = (ArrayList<String>) blobDAO.getAllBlobs();
	          }
	          else
	          {
	              blobList = (ArrayList<String>) blobDAO.getPublicBlobs();
	          }
	
	          ImagesService imagesService = ImagesServiceFactory.getImagesService();
	          // loop through files information returned from query
	          for(int i = 0; i < blobList.size(); i++) {
	              
	              key = new BlobKey(blobList.get(i));
	              blobNames.add(blobInfoFactory.loadBlobInfo(key).getFilename());
	              fileURLs.add(imagesService.getServingUrl(key));
	         }
	          
	          req.setAttribute("blobNames", blobNames);
	          req.setAttribute("fileURLs", fileURLs);
          
	          RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
	          try {
				dispatcher.forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	

