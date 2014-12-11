package be.kayiranga.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.kayiranga.dao.ImageDao;
import be.kayiranga.daoImpl.ImageDaoImpl;
import be.kayiranga.model.Image;

public class ImageDisplayer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ImageDao imageDao;

	public ImageDisplayer() {
		super();
		imageDao = new ImageDaoImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int imageId = Integer.parseInt(request.getParameter("imageId"));
		System.out.println("Image id: " + imageId);
		Image img = imageDao.getImageById(imageId);

//		response.setContentType("image/" + img.getImageType());

		// String pathToWeb = getServletContext().getRealPath(File.separator);
		File f = new File(img.getImagePath());
		BufferedImage bi = ImageIO.read(f);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, img.getImageType(), out);
		out.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
	}

}
