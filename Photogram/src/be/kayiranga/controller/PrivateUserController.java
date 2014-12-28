package be.kayiranga.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.kayiranga.dao.FollowshipDao;
import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.FollowshipDaoImpl;
import be.kayiranga.daoImpl.UserDaoImpl;
import be.kayiranga.model.User;

public class PrivateUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao;
	private FollowshipDao followshipDao;
//	private List<User> allUsers;

//	@SuppressWarnings("unchecked")
	public PrivateUserController() {
		super();
		userDao = new UserDaoImpl();
		followshipDao = new FollowshipDaoImpl();
//		allUsers = (List<User>)getServletContext().getAttribute("allUsers");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userPath = request.getServletPath();

		if (userPath.equals("/follow")) {

			HttpSession session = request.getSession(false);
			if (session.getAttribute("user") != null) {
				User follower = userDao.findUserById(Integer.parseInt(request
						.getParameter("ferid")));
				User followed = userDao.findUserById(Integer.parseInt(request
						.getParameter("fedid")));
				adaptFollowships(session, follower, followed, true);
			}
		} else if (userPath.equals("/unfollow")) {
			HttpSession session = request.getSession(false);
			if (session.getAttribute("user") != null) {
				User follower = userDao.findUserById(Integer.parseInt(request
						.getParameter("ferid")));
				User followed = userDao.findUserById(Integer.parseInt(request
						.getParameter("fedid")));
				adaptFollowships(session, follower, followed, false);
			}
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

	private void adaptFollowships(HttpSession s, User follower, User followed,
			boolean seekFollow) {
		if (followshipDao.checkFollowship(follower.getUserId(),
				followed.getUserId())
				&& !seekFollow) {
			followshipDao.deleteFollowship(follower, followed);
		} else if (!followshipDao.checkFollowship(follower.getUserId(),
				followed.getUserId()) && seekFollow) {
			followshipDao.createFollowship(follower, followed);
		}
		followshipDao.sortFollowships(follower, s);
	}
}