package be.kayiranga.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.kayiranga.dao.FollowshipDao;
import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.FollowshipDaoImpl;
import be.kayiranga.daoImpl.UserDaoImpl;
import be.kayiranga.model.User;

public class PrivateUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao;
	private FollowshipDao followshipDao;

	public PrivateUserController() {
		super();
		userDao = new UserDaoImpl();
		followshipDao = new FollowshipDaoImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userPath = request.getServletPath();
		if (userPath.equals("/follow")) {
			User follower = userDao.findUserById(Integer.parseInt(request
					.getParameter("ferId")));
			User followed = userDao.findUserById(Integer.parseInt(request
					.getParameter("fedId")));
			followshipDao.createFollowship(follower, followed);
		} else if (userPath.equals("/unfollow")) {
			User follower = userDao.findUserById(Integer.parseInt(request
					.getParameter("ferId")));
			User followed = userDao.findUserById(Integer.parseInt(request
					.getParameter("fedId")));
			followshipDao.deleteFollowship(follower, followed);
		}
		String url = "/pages/private/displayUserProfile.jsp";
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

		String url = "/pages/private" + userPath + ".jsp";

		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}