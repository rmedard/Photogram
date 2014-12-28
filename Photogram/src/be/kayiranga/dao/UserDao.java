package be.kayiranga.dao;

import java.util.List;

import be.kayiranga.model.Image;
import be.kayiranga.model.User;

public interface UserDao {
	public abstract void createUser(User user);

	public abstract void updateUser(User user);

	public abstract void deleteUser(User user);
	
	public abstract User findUserById(int userId);

	public abstract User findUserByUsername(String username);
	
	public abstract List<User> findAllUsers();

	public abstract List<User> findUserByToken(String token);

	public abstract List<User> findUserFollowees(User user);

	public abstract void follow(User follower, User followed);

	public abstract void unfollow(User follower, User followed);
	
	public abstract void setProfileImage(User user, Image image);
	
	public abstract Image getProfileImage(User user);
	
	public abstract User login(User user);
}