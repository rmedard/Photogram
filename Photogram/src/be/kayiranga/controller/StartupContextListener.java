package be.kayiranga.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.UserDaoImpl;

@WebListener
public class StartupContextListener implements ServletContextListener,
		HttpSessionListener {

	private UserDao userDao;
	private ServletContext ctx;
	private List<HttpSession> allSessions;

	public StartupContextListener() {
		userDao = new UserDaoImpl();
		allSessions = new ArrayList<HttpSession>();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		ctx = sce.getServletContext();
		ctx.setAttribute("allUsers", userDao.findAllUsers());
	}

	@Override
	public void sessionCreated(HttpSessionEvent hse) {
		allSessions.add(hse.getSession());
		ctx.setAttribute("allSessions", allSessions);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent hse) {
		allSessions.remove(hse.getSession());
		ctx.setAttribute("allSessions", allSessions);
	}
}
