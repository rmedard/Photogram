package be.kayiranga.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.kayiranga.dao.UserDao;
import be.kayiranga.model.Image;
import be.kayiranga.model.User;

public class UserDaoImpl implements UserDao {

	public UserDaoImpl() {
	}

	@Override
	public void createUser(User user) {
		String query = "INSERT INTO users(name, postname, email, username, password, userDir)"
				+ " VALUES(?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPostname());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getUsername());
			ps.setString(5, user.getPassword());
			ps.setString(6, user.getUserDir());
			ps.executeUpdate();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	@Override
	public void updateUser(User user) {
		String query = "UPDATE users SET name = ?, postname = ?, "
				+ "email = ?, username = ?, password = ?, userDir = ? WHERE userId = ?";
		PreparedStatement ps = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPostname());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getUsername());
			ps.setString(5, user.getPassword());
			ps.setString(6, user.getUserDir());
			ps.setInt(7, user.getUserId());
			ps.executeUpdate();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	@Override
	public void deleteUser(User user) {
		String query = "DELETE FROM users WHERE userId = ?";
		PreparedStatement ps = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setInt(1, user.getUserId());
			ps.executeUpdate();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	@Override
	public User findUserById(int userId) {
		String query = "SELECT * FROM users WHERE userId = ?";
		User user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			ps = connection.prepareStatement(query);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("userId"));
				user.setName(rs.getString("name"));
				user.setPostname(rs.getString("postname"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUserDir(rs.getString("userDir"));
			}
		} catch (SQLException e) {
		} finally {
			close(rs);
			close(ps);
		}
		return user;
	}

	public User findUserByUsername(String username) {
		String query = "SELECT * FROM users WHERE username = ?";
		User user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			ps = connection.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("userId"));
				user.setName(rs.getString("name"));
				user.setPostname(rs.getString("postname"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUserDir(rs.getString("userDir"));
			}
		} catch (SQLException e) {
		} finally {
			close(rs);
			close(ps);
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		String query = "SELECT * FROM users";
		List<User> users = new ArrayList<User>();
		Statement st = null;
		ResultSet rs = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			users = parseUsers(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(st);
		}
		return users;
	}

	@Override
	public List<User> findUserByToken(String token) {
		String query = "SELECT * FROM users WHERE username LIKE ? OR name LIKE ? OR postname LIKE ?";
		List<User> users = new ArrayList<User>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			ps = connection.prepareStatement(query);
			ps.setString(1, "%" + token + "%");
			ps.setString(2, "%" + token + "%");
			ps.setString(3, "%" + token + "%");
			rs = ps.executeQuery();
			users = parseUsers(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return users;
	}

	@Override
	public List<User> findUserFollowees(User user) {
		List<User> followers = new ArrayList<User>();
		String query = "SELECT * FROM users AS u LEFT JOIN followships AS "
				+ "f ON u.userId = f.followedId WHERE f.followerId=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			ps = connection.prepareStatement(query);
			ps.setInt(1, user.getUserId());
			rs = ps.executeQuery(query);
			followers = parseUsers(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return followers;
	}

	@Override
	public void follow(User follower, User followed) {
		String query = "INSERT INTO followships(followerId, followedId) VALUES(?, ?)";
		PreparedStatement ps = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setInt(1, follower.getUserId());
			ps.setInt(2, followed.getUserId());
			ps.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	@Override
	public void unfollow(User follower, User followed) {
		String query = "DELETE FROM followships WHERE followerId = ? AND followedId = ?";
		PreparedStatement ps = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setInt(1, follower.getUserId());
			ps.setInt(2, followed.getUserId());
			ps.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	@Override
	public void setProfileImage(User user, Image image) {
		String query = "UPDATE images SET isProfilePic = ? WHERE ownerId = ?";
		PreparedStatement ps = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			ps = connection.prepareStatement(query);
			connection.setAutoCommit(false);
			if (user != null && image != null
					&& image.getOwnerId() == user.getUserId()) {
				for (Image img : new ImageDaoImpl().getImagesByUser(user)) {
					if (image.equals(img)) {
						if (!image.isProfilePic()) {
							ps.setBoolean(1, true);
							ps.setInt(2, user.getUserId());
							ps.executeUpdate();
						}
					} else {
						if (image.isProfilePic()) {
							ps.setBoolean(1, false);
							ps.setInt(2, user.getUserId());
							ps.executeUpdate();
						}
					}
					connection.setAutoCommit(true);
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(ps);
		}
	}

	@Override
	public Image getProfileImage(User user) {
		return new ImageDaoImpl().getUserProfileImage(user);
	}

	@Override
	public User login(User user) {
		String query = "SELECT userId FROM users WHERE username = ? AND password = ?";
		User u = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection connection = ConnectionManager.getConnection()) {
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			rs = ps.executeQuery();
			if (rs.next()) {
				u = findUserById(rs.getInt("userId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}
		return u;
	}

	private List<User> parseUsers(ResultSet rs) {
		List<User> users = new ArrayList<User>();
		try {
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("userId"));
				user.setName(rs.getString("name"));
				user.setPostname(rs.getString("postname"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setUserDir(rs.getString("userDir"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
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

	private void close(Statement st) {
		if (!st.equals(null)) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}