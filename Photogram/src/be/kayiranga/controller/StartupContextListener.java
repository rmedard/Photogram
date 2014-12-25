package be.kayiranga.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.UserDaoImpl;

@WebListener
public class StartupContextListener implements ServletContextListener {

	private UserDao userDao;

	public StartupContextListener() {
		userDao = new UserDaoImpl();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext()
				.setAttribute("allUsers", userDao.findAllUsers());
	}

}
