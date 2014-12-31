package be.kayiranga.controller;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.UserDaoImpl;

@WebListener
public class StartupContextListener implements ServletContextListener {

	private UserDao userDao;
	private ServletContext ctx;
	
	public StartupContextListener() {
		userDao = new UserDaoImpl();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		ctx = sce.getServletContext();
		ctx.setAttribute("allUsers", userDao.findAllUsers());
		ctx.setAttribute("allUserSessions", new ArrayList<HttpSession>());
	}
}