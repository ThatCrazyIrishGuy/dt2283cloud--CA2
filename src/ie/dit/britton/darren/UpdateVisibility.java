package ie.dit.britton.darren;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UpdateVisibility extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		String key = (String) req.getParameter("blobKey");
		BlobDAO blobDAO = new BlobDAO();
		blobDAO.updateBlobVisibility(key);

		resp.sendRedirect("/getuserblobs");
	}
}