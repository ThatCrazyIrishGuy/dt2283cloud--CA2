package ie.dit.britton.darren;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class PictureBoxServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		UserService userService = UserServiceFactory.getUserService(); //gets instance of user service instance
		Principal myPrincipal = req.getUserPrincipal(); // gets instance of user principle
		String thisURL = req.getRequestURI(); //gets the current url
		String loginURL = userService.createLoginURL(thisURL); // creates login service for obtained url
		resp.setContentType("text/html"); //sets output writer for html
		if(myPrincipal == null) { //if principle empty
		resp.getWriter().println("<p>You are Not Logged In time</p>"); // writes string
		resp.getWriter().println("<p>You can <a href=\""+loginURL+ // writes another string...
		"\">sign in here</a>.</p>"); // more string
		} // end if not logged in
		if(myPrincipal !=null) { // if principle populated
		resp.sendRedirect("/upload.jsp");
		} // end if logged in
	}
}
