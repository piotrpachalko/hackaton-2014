package com.mpmp.iface.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
@Entity
@Table(name = "UserProfile")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "token")
	private String userToken;

	private Location location;

	public User() {
		// empty on purpose qrwa
	}

	public User(String userId, Location location) {
		super();
		this.userToken = userId;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((userToken == null) ? 0 : userToken.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (userToken == null) {
			if (other.userToken != null)
				return false;
		} else if (!userToken.equals(other.userToken))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userToken=" + userToken + ", preferences=" /*+ preferences */+ ", location="
				+ location + "]";
	}

}
