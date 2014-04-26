package com.mpmp.iface.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PreferenceKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "item_id", nullable = false)
	private long itemId;

	@Column(name = "user_id", nullable = false)
	private long userId;

	public PreferenceKey() {
		// empty
	}

	public PreferenceKey(long itemId, long userId) {
		super();
		this.itemId = itemId;
		this.userId = userId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "PreferenceKey [itemId=" + itemId + ", userId=" + userId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
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
		PreferenceKey other = (PreferenceKey) obj;
		if (itemId != other.itemId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

}
