package ie.dit.britton.darren;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class LoginLogout extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		UserService userService = UserServiceFactory.getUserService(); //gets instance of user service instance
		Principal myPrincipal = req.getUserPrincipal(); // gets instance of user principle
		String homeURL = req.getRequestURI(); //gets the current url
		homeURL = homeURL.substring(0,homeURL.lastIndexOf('/'));
		homeURL +="/getblobs";
		if(myPrincipal == null) { //if principle empty then user is not logged in
			String loginURL = userService.createLoginURL(homeURL); // creates login service for obtained url
			resp.sendRedirect(loginURL);
		}
		if(myPrincipal !=null) { // if principle populated then user is logged in
			String logoutURL = userService.createLogoutURL(homeURL); // creates logout service for obtained url
			resp.sendRedirect(logoutURL);
		}
	}	
}
