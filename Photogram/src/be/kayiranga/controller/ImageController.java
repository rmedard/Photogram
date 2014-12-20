package be.kayiranga.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.kayiranga.dao.ImageDao;
import be.kayiranga.dao.UserDao;
import be.kayiranga.daoImpl.ImageDaoImpl;
import be.kayiranga.daoImpl.UserDaoImpl;
import be.kayiranga.model.Image;

public class ImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ImageDao imageDao;
	private UserDao userDao;

	public ImageController() {
		super();
		imageDao = new ImageDaoImpl();
		userDao = new UserDaoImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userPath = request.getServletPath();
		if (userPath.equalsIgnoreCase("/displayImg")) {
			String imageIdParam = request.getParameter("imageId");
			Image image = null;
			if (imageIdParam != null && !imageIdParam.equals("")
					&& !imageIdParam.equals("-1")) {
				int imageId = Integer.parseInt(imageIdParam);
				image = imageDao.getImageById(imageId);
			} else if (imageIdParam != null && imageIdParam.equals("-1")) {
				image = new Image();
				image.setImageType("png");
				image.setImagePath(getServletContext().getInitParameter(
						"resources")
						+ "\\images\\anonymous.png");
			}
			response.setContentType("image/" + image.getImageType());
			Path path = Paths.get(image.getImagePath());
			OutputStream out = response.getOutputStream();
			Files.copy(path, out);
			out.close();
		} else {
			String url = "";
			if (userPath.equalsIgnoreCase("/displayProfilePic")) {
				String userId = request.getParameter("userId");
				if(userId == null || userId.equals("")){
					
				}else{
					int uId = Integer.parseInt(request.getParameter("userId"));
					Image profilePic = imageDao.getUserProfileImage(userDao
							.findUserById(uId));
					if (profilePic != null) {
						url = "/displayImg?imageId=" + profilePic.getImageId();
					} else {
						url = "/displayImg?imageId=-1";
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userPath = request.getServletPath();
		String url = "";
		if (userPath.equalsIgnoreCase("/editImage")) {
			if (request.getParameter("imageId") != null) {
				int imageId = Integer.parseInt(request.getParameter("imageId"));
				Image image = new Image();
				image.setImageId(imageId);
				image.setDescription(request.getParameter("descriptionTxt"));
				image.setOwnerId(userDao.findUserById(
						imageDao.getImageById(imageId).getOwnerId())
						.getUserId());
				image.setPublicPic(booleanParser(request
						.getParameter("publicRd")));
				image.setProfilePic(booleanParser(request
						.getParameter("profileRd")));
				if (!image.equals(imageDao.getImageById(imageId))) {
					imageDao.updateImage(image);
					request.setAttribute("active-image",
							imageDao.getImageById(imageId));
				}
			}
			url = "/pages/private/imageDataDisplay.jsp?";
		}
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean booleanParser(String value) {
		boolean bool = false;
		if (value.equalsIgnoreCase("oui") || value.equalsIgnoreCase("true")) {
			bool = true;
		} else if (value.equalsIgnoreCase("non")
				|| value.equalsIgnoreCase("false")) {
			bool = false;
		}
		return bool;
	}

}