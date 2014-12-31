package be.kayiranga.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import be.kayiranga.dao.FollowshipDao;
import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.FollowshipDaoImpl;
import be.kayiranga.daoImpl.UserDaoImpl;
import be.kayiranga.model.User;

public class SessionTools {

	private UserDao userDao;
	private FollowshipDao followshipDao;

	public SessionTools() {
		super();
		userDao = new UserDaoImpl();
		followshipDao = new FollowshipDaoImpl();
	}

	public void update(List<HttpSession> sessions) {
		for (HttpSession httpSession : sessions) {
			if (httpSession!= null && httpSession.getAttribute("userId") != null) {
				User user = userDao.findUserById(Integer.parseInt(httpSession
						.getAttribute("userId").toString()));
				followshipDao.sortFollowships(user, httpSession);
			}
		}
	}
	
	public User getLoggedInUser(HttpServletRequest req){
		HttpSession session = req.getSession(false);
		User user = null;
		if(session != null){
			if(session.getAttribute("userId")!= null){
				int userId = Integer.parseInt(session.getAttribute("userId").toString());
				user = userDao.findUserById(userId);
			}
		}
		return user;
	}
	
}