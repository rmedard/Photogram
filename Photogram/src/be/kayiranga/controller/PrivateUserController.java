package be.kayiranga.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.UserDaoImpl;

@WebServlet(name = "privateUC", loadOnStartup = 1, urlPatterns = {
		"/editAddUser", "/displayUsers", "/follow", "/unfollow",
		"/uploadImage" })
public class PrivateUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	private static String INSERT_OR_UPDATE = "/user.jsp";
//	private static String LIST_USERS = "/allUsers.jsp";
	private UserDao userDao;

	public PrivateUserController() {
		super();
		userDao = new UserDaoImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userPath = request.getServletPath();
		if (userPath.equals("/editAddUser")) {

		} else if (userPath.equals("/displayUsers")) {

		}

		String url = "/pages/private" + userPath + ".jsp";

		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String userPath = request.getServletPath();
		if (userPath.equals("/editAddUser")) {

		} else if (userPath.equals("/follow")) {

		} else if (userPath.equals("/unfollow")) {

		} else if (userPath.equals("/uploadImage")) {

		}

		String url = "/pages/private" + userPath + ".jsp";

		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}