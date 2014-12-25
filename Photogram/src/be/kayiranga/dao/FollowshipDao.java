package be.kayiranga.dao;

import java.util.List;

import be.kayiranga.model.User;

public interface FollowshipDao {
	public abstract void createFollowship(User follower, User followed);

	public abstract void deleteFollowship(User follower, User followed);

	public abstract List<User> getUserFollowers(int userId);
	
	public boolean checkFollowship(User follower, User followed);
}
