package ie.dit.britton.darren.servlet;

import ie.dit.britton.darren.dao.BlobDAO;

import java.io.IOException;







import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** 
* UpdateVisibility.java - Servlet that encapsulates the update of blob visibility via the BlobDAO. 
* @author  Darren Britton
* @see HttpServlet
* @See BlobDAO
*/
@SuppressWarnings("serial")
public class UpdateVisibility extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {		
		BlobDAO blobDAO = new BlobDAO();
		UserService userService = UserServiceFactory.getUserService();

		String key = (String) req.getParameter("blobKey");
		String returnTo = (String) req.getParameter("returnTo");

		if(blobDAO.getBlobOwner(key).equals(String.valueOf(userService.getCurrentUser())) || userService.isUserAdmin())
		{
			blobDAO.updateBlobVisibility(key);
		}

		resp.sendRedirect("/" + returnTo);
	}
}