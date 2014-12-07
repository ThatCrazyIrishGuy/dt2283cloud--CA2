package ie.dit.britton.darren;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.com.google.common.collect.Multimap;

@SuppressWarnings("serial")
public class GetUserBlobs extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		BlobDAO blobDAO = new BlobDAO();
		PictureService pictureService = new PictureService((ArrayList < String > ) blobDAO.getAllBlobs());
		Multimap < String, String > picInfoMap = pictureService.getFullInfo();

		req.setAttribute("picInfoMap", picInfoMap);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/myImages.jsp");
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}