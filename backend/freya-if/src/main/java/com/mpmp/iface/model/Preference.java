package com.mpmp.iface.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "taste_preferences")
public class Preference implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PreferenceKey prefKey;

	@Column(name = "preference")
	private float score;

	public Preference() {
		// empty preference
	}

	public Preference(PreferenceKey prefKey, float score) {
		super();
		this.prefKey = prefKey;
		this.score = score;
	}

	public PreferenceKey getPrefKey() {
		return prefKey;
	}

	public void setPrefKey(PreferenceKey prefKey) {
		this.prefKey = prefKey;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Preference [prefKey=" + prefKey + ", score=" + score + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prefKey == null) ? 0 : prefKey.hashCode());
		result = prime * result + Float.floatToIntBits(score);
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
		Preference other = (Preference) obj;
		if (prefKey == null) {
			if (other.prefKey != null)
				return false;
		} else if (!prefKey.equals(other.prefKey))
			return false;
		if (Float.floatToIntBits(score) != Float.floatToIntBits(other.score))
			return false;
		return true;
	}

}
