package be.kayiranga.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;

import be.kayiranga.dao.ImageDao;
import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.ImageDaoImpl;
import be.kayiranga.daoImpl.UserDaoImpl;
import be.kayiranga.model.Image;
import be.kayiranga.model.User;

public class PublicUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao;
	private ImageDao imageDao;

	public PublicUserController() {
		super();
		userDao = new UserDaoImpl();
		imageDao = new ImageDaoImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userPath = request.getServletPath();
		String url = "";
		if (userPath.equals("/editAddUser")) {
			HttpSession session = request.getSession(true);
			if (session != null && (session.getAttribute("user")) != null) {
				User u = (User) session.getAttribute("user");
				if (u != null) {
					request.setAttribute("newUser", false);
					request.setAttribute("name", u.getName());
					request.setAttribute("postname", u.getPostname());
					request.setAttribute("email", u.getEmail());
					request.setAttribute("username", u.getUsername());
					request.setAttribute("password", u.getPassword());
					request.setAttribute("newUser", false);
				} else {
					request.setAttribute("newUser", true);
					userPath = "/login";
				}
			} else {
				request.setAttribute("newUser", true);
				userPath = "/editAddUser";
			}
		} else if (userPath.equals("/login")
				&& request.getParameter("action").equalsIgnoreCase("logout")) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.setAttribute("loggedIn", false);
				session.invalidate();
				session = request.getSession(true);
			}
			url = "/pages/public" + userPath + ".jsp";
		} 
		// String url = "/pages/public" + userPath + ".jsp";

		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userPath = request.getServletPath();
		String url = "";
		if (userPath.equals("/login")) {
			String action = request.getParameter("action");
			if (action.equalsIgnoreCase("login")) {
				User user = new User();
				user.setUsername(request.getParameter("usernameTxt"));
				user.setPassword(request.getParameter("passwordTxt"));
				User loggedInUser = userDao.login(user);
				if (loggedInUser != null) {
					HttpSession session = request.getSession(true);
					session.setAttribute("user", loggedInUser);
					session.setAttribute("loggedIn", true);
					// session.setAttribute("images",
					// imageDao.getImagesByUser(loggedInUser));

					request.setAttribute("user", loggedInUser);
					request.setAttribute("username", loggedInUser.getUsername());
					request.setAttribute("loggedIn", true);
					request.setAttribute("images",
							imageDao.getImagesByUser(loggedInUser));
					userPath = "/displayUserProfile";
					url = "/pages/private" + userPath + ".jsp";
				} else {
					url = "/pages/public" + userPath + ".jsp";
				}
			}
		}

		else if (userPath.equals("/editAddUser")) {
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute("user") != null) {
				User user = new User();
				User updatedUser = (User) session.getAttribute("user");
				user.setUserId(updatedUser.getUserId());
				user.setName(request.getParameter("nameTxt"));
				user.setPostname(request.getParameter("postnameTxt"));
				user.setUsername(request.getParameter("usernameTxt"));
				user.setEmail(request.getParameter("emailTxt"));
				user.setPassword(request.getParameter("passwordTxt"));

				if (updatedUser.getUserDir() == null) {
					updatedUser.setUserDir(getServletContext()
							.getInitParameter("image-upload")
							+ "\\"
							+ user.getUsername()
							+ "_"
							+ RandomStringUtils.randomAlphanumeric(6));
				}
				user.setUserDir(updatedUser.getUserDir());
				File userDir = new File(updatedUser.getUserDir());
				if (!userDir.exists()
						|| (userDir.exists() && !userDir.isDirectory())) {
					userDir.mkdir();
				} else if (userDir.exists()) {
					if (!user.getUsername().equals(updatedUser.getUsername())) {
						// Rename the user directory name
						user.setUserDir(getServletContext().getInitParameter(
								"image-upload")
								+ "\\"
								+ user.getUsername()
								+ updatedUser.getUserDir().substring(
										updatedUser.getUserDir().length() - 7));
						File f = new File(updatedUser.getUserDir());
						if (f.exists() && f.isDirectory()) {
							File updatedDir = new File(user.getUserDir());
							f.renameTo(updatedDir);
						}
					}
				}
				userDao.updateUser(user);
				session.setAttribute("user", user);
				// request.setAttribute("user", user);
			} else {
				User user = new User();
				user.setName(request.getParameter("nameTxt"));
				user.setPostname(request.getParameter("postnameTxt"));
				user.setUsername(request.getParameter("usernameTxt"));
				user.setEmail(request.getParameter("emailTxt"));
				user.setPassword(request.getParameter("passwordTxt"));
				user.setUserDir(getServletContext().getInitParameter(
						"image-upload")
						+ "\\"
						+ user.getUsername()
						+ "_"
						+ RandomStringUtils.randomAlphanumeric(6));
				userDao.createUser(user);
				File userDir = new File(user.getUserDir());
				if (!userDir.exists()
						|| (userDir.exists() && !userDir.isDirectory())) {
					userDir.mkdir();
				}
				session.setAttribute("user", user);
			}
			url = "/pages/private/displayUserProfile.jsp";
		} else if (userPath.equalsIgnoreCase("/uploadImage")) {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			HttpSession session = request.getSession(false);
			if (isMultipart) {
				try {
					List<FileItem> multiparts = new ServletFileUpload(
							new DiskFileItemFactory()).parseRequest(request);
					for (FileItem fileItem : multiparts) {
						if (!fileItem.isFormField()) {
							User loggedInUser = (User) session
									.getAttribute("user");
							if (loggedInUser != null) {
								String dir = loggedInUser.getUserDir();
								if (dir != null) {
									File f = new File(dir);
									String fileName = fileItem.getName();
									if (!f.exists()
											|| (f.exists() && !f.isDirectory())) {
										f.mkdirs();
									}
									File file = new File(f + "\\" + fileName);

									fileItem.write(file);
									Image image = new Image(file.getPath(),
											FilenameUtils
													.getExtension(fileName),
											loggedInUser.getUserId(), false,
											true);
									imageDao.createImage(image);
									request.setAttribute("message",
											"Photo t�l�charg�e avec succ�s!");

								} else {
									System.out.println("Dir is null....");
								}
							}
						}
					}
				} catch (Exception e) {
					request.setAttribute("message",
							"Ech�c de t�l�chargement...");
					e.printStackTrace();
				}
			}
		}
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}