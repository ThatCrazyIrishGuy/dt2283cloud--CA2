package ie.dit.britton.darren.servlet;

import ie.dit.britton.darren.dao.BlobDAO;
import ie.dit.britton.darren.service.PictureService;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.com.google.common.collect.Multimap;

/** 
* GetUserBlobs.java - a servlet that handles getting getting blobs based on if the user is logged in
* or not and passing them to myImages.jsp as a Multimap. 
* @author  Darren Britton
* @See BlobDAO
* @see HttpServlet
* @see Multimap
*/
@SuppressWarnings("serial")
public class GetUserBlobs extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		BlobDAO blobDAO = new BlobDAO();
		PictureService pictureService = new PictureService((ArrayList < String > ) blobDAO.getUserBlobs());
		Multimap < String, String > picInfoMap = pictureService.getFullInfo();

		req.setAttribute("picInfoMap", picInfoMap);
		req.setAttribute("baseServlet", "getuserblobs");

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/myImages.jsp");
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}