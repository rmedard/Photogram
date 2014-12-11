package be.kayiranga.dao;

import java.util.List;

import be.kayiranga.model.Image;
import be.kayiranga.model.User;

public interface ImageDao {
	public abstract void createImage(Image image);

	public abstract void deleteImage(Image image);
	
	public abstract void updateImage(Image image);

	public abstract Image getImageById(int imageId);
	
	public abstract Image getUserProfileImage(User user);

	public abstract List<Image> getImagesByUser(User user);

}
