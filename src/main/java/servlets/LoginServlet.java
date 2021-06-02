
package servlets;


import dao.DAOException;
import doMain.User;
import org.apache.log4j.Logger;
import service.IUserService;
import service.impl.UserServiceImpl;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(LoginServlet.class);

    private IUserService userService = UserServiceImpl.getUserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.trace("Opening Login Form...");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = null;

        log.trace("Checking fields values for emptiness...");

        if (!login.isEmpty() && !password.isEmpty()) {
            try {
                log.trace("Getting user from database...");
                user = userService.readByEmail(login);
            } catch (DAOException e) {
                log.error("Getting user by email failed!", e);
            }

        if (user == null) {
            log.warn("There is no user with login \"" + login + "\" in database!");
            log.trace("Reopening Login Form...");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        else {
            log.trace("Checking user's password for matching database...");

            if (user.getPassword().equals(password)){
                log.trace("Preparing fields to return...");
                request.setAttribute("userFirstName", user.getFirstName());
                request.setAttribute("userLastName", user.getLastName());
                request.setAttribute("userAction", "увійшов в акаунт");
                log.trace("Redirecting to User's account page...");
                request.getRequestDispatcher("cabinet.jsp").forward(request, response);
                return;
            }
            else {
                log.warn("User's password doesn't match database!");
                log.trace("Reopening Login Form...");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }

        if (user.getPassword().equals(password)) {
            request.setAttribute("userFirstName", user.getFirstName());
            request.setAttribute("userLastName", user.getLastName());
            request.setAttribute("userAction", "авторизувався");

            request.getRequestDispatcher("cabinet.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
        else {
            log.warn("There are still some blank fields yet!");
            log.trace("Reopening Login Form...");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

}
}
