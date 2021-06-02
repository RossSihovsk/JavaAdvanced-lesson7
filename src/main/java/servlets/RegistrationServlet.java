package servlets;

import doMain.AccessLevel;
import doMain.User;
import org.apache.log4j.Logger;
import service.IUserService;
import service.impl.UserServiceImpl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IUserService userService = UserServiceImpl.getUserService();

    private Logger log = Logger.getLogger(RegistrationServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        log.trace("Opening Registration Form...");
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String accessLevel = null;
		
		if ("user".equals(request.getParameter("accessLevel"))) {
			accessLevel = AccessLevel.USER.toString();
		} else if ("admin".equals(request.getParameter("accessLevel"))) {
			accessLevel = AccessLevel.ADMIN.toString();
		}
				
		if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty()) { 
			//userService.saveUser(new User(firstName, lastName, email, password, accessLevel));
            try{
                log.trace("Saving user in database...");
                userService.insert(new User(firstName, lastName, email, password, accessLevel));

            }
            catch (Exception exception) {
                log.error("Creating user failed!", exception);
            }

            request.setAttribute("userFirstName", firstName);
			request.setAttribute("userLastName", lastName);
			request.setAttribute("userAction", "Пройшов ргістрацію!");
			
			request.getRequestDispatcher("cabinet.jsp").forward(request, response);
			return;
	  	}
		
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

}
