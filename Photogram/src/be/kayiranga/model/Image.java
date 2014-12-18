package be.kayiranga.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Image implements Serializable {

	private static final long serialVersionUID = 1L;
	private int imageId;
	private String imagePath;
	private String imageType;
	private int ownerId;
	private boolean profilePic;
	private boolean publicPic;
	private String description;
	private Timestamp createdOn;

	public Image() {
		super();
	}

	public Image(String imagePath, String imageType, int ownerId, boolean isProfilePic,
			boolean isPublic) {
		super();
		this.imagePath = imagePath;
		this.imageType = imageType;
		this.ownerId = ownerId;
		this.profilePic = isProfilePic;
		this.publicPic = isPublic;
	}

	public Image(int imageId, String imageFile, String imageType, int ownerId,
			boolean isProfilePic, boolean isPublic, String description,
			Timestamp createdOn) {
		super();
		this.imageId = imageId;
		this.imagePath = imageFile;
		this.imageType = imageType;
		this.ownerId = ownerId;
		this.profilePic = isProfilePic;
		this.publicPic = isPublic;
		this.description = description;
		this.createdOn = createdOn;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isProfilePic() {
		return profilePic;
	}

	public void setProfilePic(boolean isProfilePic) {
		this.profilePic = isProfilePic;
	}

	public boolean isPublicPic() {
		return publicPic;
	}

	public void setPublicPic(boolean isPublic) {
		this.publicPic = isPublic;
	}

	public int getImageId() {
		return imageId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + imageId;
		result = prime * result + (profilePic ? 1231 : 1237);
		result = prime * result + (publicPic ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (imageId != other.imageId)
			return false;
		if (profilePic != other.profilePic)
			return false;
		if (publicPic != other.publicPic)
			return false;
		return true;
	}

}