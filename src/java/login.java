import java.io.IOException;
import static java.lang.System.out;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBinfo loginDao = new DBinfo();
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		LoginBean loginBean = new LoginBean();
		loginBean.setID(id);
		loginBean.setPassword(password);
		
		if (loginDao.validate(loginBean))
		{
                        HttpSession session = request.getSession(true);
                        session.setAttribute("sessionCustomerID", id);
			response.sendRedirect("customerhome.jsp");
                        
			
		}
		else 
		{
			//HttpSession session = request.getSession();
                      
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/login.html");
                        out.println("<h4 style=\"color: red; font-size: 20px; text-align: center;\">Either user name or password is wrong.</h4>");
			rd.include(request, response);
		}
		
	}

}
