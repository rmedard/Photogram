package be.kayiranga.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import be.kayiranga.dao.FollowshipDao;
import be.kayiranga.dao.ImageDao;
import be.kayiranga.dao.UserDao;
import be.kayiranga.model.Image;
import be.kayiranga.model.User;

public class FollowshipDaoImpl implements FollowshipDao {

	private UserDao userDao;
	private ImageDao imageDao;

	public FollowshipDaoImpl() {
		userDao = new UserDaoImpl();
		imageDao = new ImageDaoImpl();
	}

	@Override
	public void createFollowship(User follower, User followed) {
		String query = "INSERT INTO followships(followerId, followedId) VALUES(?,?)";
		PreparedStatement ps = null;
		try (Connection con = ConnectionManager.getConnection()) {
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);
			ps.setInt(1, follower.getUserId());
			ps.setInt(2, followed.getUserId());
			ps.executeUpdate();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	@Override
	public void deleteFollowship(User follower, User followed) {
		String query = "DELETE FROM followships WHERE followerId=? AND followedId=?";
		PreparedStatement ps = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			ps = connection.prepareStatement(query);
			ps.setInt(1, follower.getUserId());
			ps.setInt(2, followed.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}

	@Override
	public List<User> getUserFollowees(int userId) {
		String query = "SELECT followedId AS fid FROM followships WHERE followerId = ?";
		List<User> followees = new ArrayList<User>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			ps = connection.prepareStatement(query);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = userDao.findUserById(rs.getInt("fid"));
				followees.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return followees;
	}

	@Override
	public boolean checkFollowship(int followerId, int followedId) {
		String query = "SELECT COUNT(DISTINCT followshipId) AS FID FROM followships "
				+ "WHERE followerId = ? AND followedId = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean followshipExists = false;
		try (Connection con = ConnectionManager.getConnection()) {
			ps = con.prepareStatement(query);
			ps.setInt(1, followerId);
			ps.setInt(2, followedId);
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt("FID") == 0) {
				followshipExists = false;
			} else if (rs.getInt("FID") == 1) {
				followshipExists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return followshipExists;
	}
	
	@Override
	public void sortFollowships(User user, HttpSession s) {
		List<User> allUsers = userDao.findAllUsers();
		List<User> followees = new ArrayList<User>();
		List<User> nonfollowees = new ArrayList<User>();
		List<Image> followeeImages = new ArrayList<Image>();
		for (User u : allUsers) {
			if (!user.equals(u)) {
				if (checkFollowship(user.getUserId(),
						u.getUserId())) {
					followees.add(u);
					followeeImages.addAll(imageDao.getImagesByUser(u));
				} else {
					nonfollowees.add(u);
				}
			}
		}
		s.setAttribute("followees", followees);
		s.setAttribute("nonfollowees", nonfollowees);
		s.setAttribute("followeeImages", followeeImages);
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
