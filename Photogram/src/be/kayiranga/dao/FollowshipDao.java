package be.kayiranga.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import be.kayiranga.model.User;

public interface FollowshipDao {
	public abstract void createFollowship(User follower, User followed);

	public abstract void deleteFollowship(User follower, User followed);
	
	public abstract void sortFollowships(User user, HttpSession s);
	
	public abstract void sortFollowships(User user, HttpServletRequest req, List<User> users);
	
	public abstract List<User> getUserFollowees(int userId);
	
	public boolean checkFollowship(int followerId, int followedId);
}
