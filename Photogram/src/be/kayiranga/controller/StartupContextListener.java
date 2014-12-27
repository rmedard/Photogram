package be.kayiranga.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.UserDaoImpl;

@WebListener
public class StartupContextListener implements ServletContextListener,
		HttpSessionListener {

	private UserDao userDao;

	public StartupContextListener() {
		userDao = new UserDaoImpl();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();
		ctx.setAttribute("allUsers", userDao.findAllUsers());
	}

	@Override
	public void sessionCreated(HttpSessionEvent hse) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
