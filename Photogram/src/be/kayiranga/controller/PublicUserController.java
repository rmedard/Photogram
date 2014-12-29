package be.kayiranga.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import be.kayiranga.dao.FollowshipDao;
import be.kayiranga.dao.ImageDao;
import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.FollowshipDaoImpl;
import be.kayiranga.daoImpl.ImageDaoImpl;
import be.kayiranga.daoImpl.UserDaoImpl;
import be.kayiranga.model.Image;
import be.kayiranga.model.User;

public class PublicUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao;
	private ImageDao imageDao;
	private FollowshipDao followshipDao;
	private UpdateSessions updateSessions;

	public PublicUserController() {
		super();
		userDao = new UserDaoImpl();
		imageDao = new ImageDaoImpl();
		followshipDao = new FollowshipDaoImpl();
		updateSessions = new UpdateSessions();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userPath = request.getServletPath();
		String url = "";
		@SuppressWarnings("unchecked")
		List<HttpSession> sessions = (ArrayList<HttpSession>) request
				.getServletContext().getAttribute("allUserSessions");
		if (userPath.equals("/images")) {
			if (request.getParameter("userId") != null) {
				int userId = Integer.parseInt(request.getParameter("userId"));
				List<Image> userImages = imageDao.getImagesByUser(userDao
						.findUserById(userId));
				request.setAttribute("imgs", userImages);
			}
		} else if (userPath.equals("/login")
				&& request.getParameter("action").equalsIgnoreCase("logout")) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.setAttribute("loggedIn", false);
				
				sessions.remove(session);
				session.invalidate();
				session = request.getSession(true);
			}
			url = "/pages/public" + userPath + ".jsp";
		} else if (userPath.equalsIgnoreCase("/imagedatadisplay")) {
			if (request.getParameter("imageId") != null) {
				Image img = null;
				if (!request.getParameter("imageId").equals("-1")) {
					int imageId = Integer.parseInt(request
							.getParameter("imageId"));
					img = imageDao.getImageById(imageId);
				} else {
					img = imageDao.getUserProfileImage((User) request
							.getSession(false).getAttribute("user"));
				}
				if (request.getParameter("owner") != null) {
					if (Boolean.parseBoolean(request.getParameter("owner"))) {
						request.setAttribute("owner", true);
					} else {
						request.setAttribute("owner", false);
					}
				}
				request.setAttribute("active-image", img);
				url = "/pages/private/imageDataDisplay.jsp";
			}
		}
		try {
			updateSessions.update(sessions);
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userPath = request.getServletPath();
		String url = "";
		@SuppressWarnings("unchecked")
		List<HttpSession> sessions = (ArrayList<HttpSession>) request
				.getServletContext()
				.getAttribute("allUserSessions");
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
					session.setAttribute("userId", loggedInUser.getUserId());
					sessions.add(session);
					request.getServletContext().setAttribute("allUserSessions",
							sessions);
					List<Image> userImages = new ArrayList<Image>();
					userImages = imageDao.getImagesByUser(loggedInUser);
					session.setAttribute("images", userImages);
					followshipDao.sortFollowships(loggedInUser, session);

					request.setAttribute("user", loggedInUser);
					request.setAttribute("username", loggedInUser.getUsername());
					request.setAttribute("images", userImages);
					userPath = "/displayUserProfile";
					url = "/pages/private" + userPath + ".jsp";
				} else {
					url = "/pages/public" + userPath + ".jsp";
				}
			}
		}

		else if (userPath.equals("/editAddUser")) {
			HttpSession session = request.getSession(false);
			String reqParam = request.getParameter("newRecord");
			if (reqParam != null && reqParam.equals("false")) {
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
						// Rename user images pathnames
						for (Image image : imageDao.getImagesByUser(user)) {
							image.setImagePath(user.getUserDir()
									+ "\\"
									+ image.getImagePath().substring(
											image.getImagePath().lastIndexOf(
													"\\")));
							imageDao.updateImage(image);
						}
					}
				}
				userDao.updateUser(user);
				session.setAttribute("user", user);
			} else if (reqParam != null && reqParam.equals("true")) {
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
				User newUser = userDao.login(user);
				File userDir = new File(user.getUserDir());
				if (!userDir.exists()
						|| (userDir.exists() && !userDir.isDirectory())) {
					userDir.mkdir();
				}
				session.setAttribute("user", newUser);
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
									session.setAttribute("images", imageDao
											.getImagesByUser(loggedInUser));
									request.setAttribute("info_message",
											"Photo téléchargée avec succès!");
									url = "/pages/private/displayUserProfile.jsp";
								} else {

								}
							}
						}
					}
				} catch (Exception e) {
					request.setAttribute("err_message",
							"Echèc de téléchargement...");
					e.printStackTrace();
				}
			}
		}
		try {
			updateSessions.update(sessions);;
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}