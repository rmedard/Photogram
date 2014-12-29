package be.kayiranga.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import be.kayiranga.dao.FollowshipDao;
import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.FollowshipDaoImpl;
import be.kayiranga.daoImpl.UserDaoImpl;
import be.kayiranga.model.User;

public class UpdateSessions {

	private UserDao userDao;
	private FollowshipDao followshipDao;

	public UpdateSessions() {
		super();
		userDao = new UserDaoImpl();
		followshipDao = new FollowshipDaoImpl();
	}

	public void update(List<HttpSession> sessions) {
		for (HttpSession httpSession : sessions) {
			if (httpSession.getAttribute("userId") != null) {
				User user = userDao.findUserById(Integer.parseInt(httpSession
						.getAttribute("userId").toString()));
				followshipDao.sortFollowships(user, httpSession);
			}
		}
	}
	
}