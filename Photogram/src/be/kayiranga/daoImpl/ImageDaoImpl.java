package be.kayiranga.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.kayiranga.dao.ImageDao;
import be.kayiranga.dao.UserDao;
import be.kayiranga.model.Image;
import be.kayiranga.model.User;

public class ImageDaoImpl implements ImageDao {

//	private Connection connection = null;

	@Override
	public void createImage(Image image) {
		try(Connection connection = ConnectionManager.getConnection()) {
			connection.setAutoCommit(false);
			String query = "INSERT INTO images(imagePath, imageType, ownerId, isProfilePic, isPublic,"
					+ " description) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(query);
			try {
				ps.setString(1, image.getImagePath());
				String imageType = "";
				if (image.getImageType().contains("jp")) {
					imageType = "jpeg";
				} else if (image.getImageType().contains("gif")) {
					imageType = "gif";
				} else if (image.getImageType().contains("png")) {
					imageType = "png";
				}
				ps.setString(2, imageType);
				ps.setInt(3, image.getOwnerId());
				ps.setBoolean(4, image.isProfilePic());
				ps.setBoolean(5, image.isPublicPic());
				ps.setString(6, image.getDescription());
				ps.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(ps);
				connection.setAutoCommit(true);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void updateImage(Image image) {
		String query = "UPDATE images SET isProfilePic = ?, "
				+ "isPublic = ?, description = ? WHERE imageId = ?";
		PreparedStatement ps = null;
		try (Connection connection = ConnectionManager.getConnection()){
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			if (image.isProfilePic()) {
				UserDao userDao = new UserDaoImpl();
				userDao.setProfileImage(
						userDao.findUserById(image.getOwnerId()), image);
			} else {
				ps.setBoolean(1, image.isProfilePic());
			}
			ps.setBoolean(2, image.isPublicPic());
			ps.setString(3, image.getDescription());
			ps.setInt(4, image.getImageId());
			ps.executeUpdate();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	@Override
	public void deleteImage(Image image) {
		Image img = getImageById(image.getImageId());
		if (!img.equals(null)) {
			PreparedStatement ps = null;
			try (Connection connection = ConnectionManager.getConnection()){
				connection.setAutoCommit(false);
				String query = "DELETE from images WHERE imageId = ?";
				ps = connection.prepareStatement(query);
				ps.setInt(1, img.getImageId());
				ps.executeUpdate();
				connection.commit();
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				close(ps);
			}
		}
	}

	@Override
	public Image getImageById(int imageId) {
		String query = "SELECT * FROM images WHERE imageId=? LIMIT 1";
		Image img = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection con = ConnectionManager.getConnection()) {
			ps = con.prepareStatement(query);
			ps.setInt(1, imageId);
			rs = ps.executeQuery();
			if (rs.first()) {
				img = new Image();
				img.setImageId(rs.getInt("imageId"));
				img.setImagePath(rs.getString("imagePath"));
				img.setImageType(rs.getString("imageType"));
				img.setOwnerId(rs.getInt("ownerId"));
				img.setProfilePic(rs.getBoolean("isProfilePic"));
				img.setPublicPic(rs.getBoolean("isPublic"));
				img.setDescription(rs.getString("description"));
				img.setCreatedOn(rs.getTimestamp("createdOn"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return img;
	}

	@Override
	public Image getUserProfileImage(User user) {
		String query = "SELECT * FROM images WHERE ownerId = ? AND isProfilePic = 1";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Image img = null;
		try (Connection connection = ConnectionManager.getConnection()){
			ps = connection.prepareStatement(query);
			ps.setInt(1, user.getUserId());
			rs = ps.executeQuery();
			if (rs.next()) {
				img = new Image();
				img.setImageId(rs.getInt("imageId"));
				img.setImagePath(rs.getString("imagePath"));
				img.setImageType(rs.getString("imageType"));
				img.setOwnerId(rs.getInt("ownerId"));
				img.setProfilePic(rs.getBoolean("isProfilePic"));
				img.setPublicPic(rs.getBoolean("isPublic"));
				img.setDescription(rs.getString("description"));
				img.setCreatedOn(rs.getTimestamp("createdOn"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return img;
	}

	@Override
	public List<Image> getImagesByUser(User user) {
		List<Image> imagesByUser = new ArrayList<Image>();
		String query = "SELECT * FROM images WHERE ownerId=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection connection = ConnectionManager.getConnection()){
			ps = connection.prepareStatement(query);
			ps.setInt(1, user.getUserId());
			rs = ps.executeQuery();
			imagesByUser = parseImages(rs);
		} catch (SQLException e) {
		} finally {
			close(rs);
			close(ps);
		}
		return imagesByUser;
	}

	private List<Image> parseImages(ResultSet rs) {
		List<Image> images = new ArrayList<Image>();
		try {
			while (rs.next()) {
				Image img = new Image();
				img.setImageId(rs.getInt("imageId"));
				img.setImagePath(rs.getString("imagePath"));
				img.setImageType(rs.getString("imageType"));
				img.setOwnerId(rs.getInt("ownerId"));
				img.setProfilePic(rs.getBoolean("isProfilePic"));
				img.setPublicPic(rs.getBoolean("isPublic"));
				img.setDescription(rs.getString("description"));
				img.setCreatedOn(rs.getTimestamp("createdOn"));
				images.add(img);
			}
		} catch (SQLException e) {
		}
		return images;
	}

	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void close(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}