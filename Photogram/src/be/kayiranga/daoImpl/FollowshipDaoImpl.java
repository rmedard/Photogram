package be.kayiranga.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import be.kayiranga.dao.FollowshipDao;
import be.kayiranga.model.User;

public class FollowshipDaoImpl implements FollowshipDao {

	@Override
	public void createFollowship(User follower, User followed) {
		String query = "INSERT INTO followships(followshipId, followerId, followedId) VALUES(?,?,?)";
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
		}finally{
			close(ps);
		}
	}

	@Override
	public void deleteFollowship(User follower, User followed) {
	}

	@Override
	public List<User> getUserFollowers(int userId) {
		return null;
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
