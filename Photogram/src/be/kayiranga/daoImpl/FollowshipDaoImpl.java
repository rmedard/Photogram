package be.kayiranga.daoImpl;

import java.util.List;

import be.kayiranga.dao.FollowshipDao;
import be.kayiranga.model.User;

public class FollowshipDaoImpl implements FollowshipDao {

	@Override
	public void createFollowship(User follower, User followed) {
	}

	@Override
	public void deleteFollowship(User follower, User followed) {
	}

	@Override
	public List<User> getUserFollowers(int userId) {
		return null;
	}
	
	
}
