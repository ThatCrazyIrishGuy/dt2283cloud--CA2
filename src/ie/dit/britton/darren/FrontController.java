//package ie.dit.britton.darren;
//
//import java.io.IOException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//
//public class FrontController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public FrontController() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		processRequest (request, response);
//	}
//
//	
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		processRequest(request, response);
//	}	
//	
//	
//	/**
//	 * Common method to process all client requests (GET and POST)
//	 */
//	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
//
//		String forwardToJsp = null;		
//		String action = request.getParameter("action");
//			
//		if (action.equalsIgnoreCase("CREATE_TRANSACTION") ){
//			
//			HttpSession session = request.getSession();
//			Transaction transaction = new Transaction();			
//			
//			String[] barcodes = request.getParameterValues("barcode[]");
//			
//			boolean exists = false;
//			
//			for (String barcode : barcodes)
//			{
//				exists = transaction.addItem(barcode);
//				
//				if (!exists)
//				{
//					try
//					{
//						response.sendRedirect("index.jsp#error");
//					} catch (IOException e)
//					{
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					return;
//				}
//				
//			}
//			
//			session.setAttribute("transaction", transaction);
//			session.setAttribute("total", transaction.getTotal());
//			
//			forwardToJsp = "/transaction.jsp";
//			
//			forwardToPage(request, response, forwardToJsp);
//		}	
//		else if (action.equalsIgnoreCase("COMMIT_TRANSACTION"))
//		{
//			HttpSession session = request.getSession();
//			
//			Transaction transaction = (Transaction) session.getAttribute("transaction");
//			transaction.commit();
//			
//			forwardToJsp = "/index.jsp";
//			
//			forwardToPage(request, response, forwardToJsp);
//		}
//		
//	}
//	
//	
//	/**
//	 * Forward to server to the supplied page
//	 */
//	private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page){
//		
//		//Get the request dispatcher object and forward the request to the appropriate JSP page...
//		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//		try {
//			dispatcher.forward(request, response);
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//}
