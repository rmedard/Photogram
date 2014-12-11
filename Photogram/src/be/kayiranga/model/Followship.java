package be.kayiranga.model;

import java.io.Serializable;

public class Followship implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int followshipId;
	private int followerId;
	private int followedId;

	public Followship() {
		super();
	}

	public Followship(int followerId, int followedId) {
		super();
		this.followerId = followerId;
		this.followedId = followedId;
	}

	public int getFollowshipId() {
		return followshipId;
	}

	public void setFollowshipId(int followshipId) {
		this.followshipId = followshipId;
	}

	public int getFollowerId() {
		return followerId;
	}

	public void setFollowerId(int followerId) {
		this.followerId = followerId;
	}

	public int getFollowedId() {
		return followedId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + followedId;
		result = prime * result + followerId;
		result = prime * result + followshipId;
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
		Followship other = (Followship) obj;
		if (followedId != other.followedId)
			return false;
		if (followerId != other.followerId)
			return false;
		if (followshipId != other.followshipId)
			return false;
		return true;
	}

}
