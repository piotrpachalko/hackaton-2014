package com.mpmp.iface.service;

import java.util.Date;

import com.mpmp.iface.model.User;

/**
 * User service
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public interface UserService {

	/**
	 * Updates user location.
	 * 
	 * <p>
	 * NOTE: If user does not exist one will be created with the provided userId
	 * </p>
	 * 
	 * @param userToken
	 *            id of user to update location
	 * @param latitude
	 *            latitude of user
	 * @param longitude
	 *            longitude of user
	 * @param time
	 *            location acquisation timestamp
	 */
	void updateLocation(String userToken, double latitude, double longitude, Date time);

	/**
	 * Updates user item preferences
	 * 
	 * @param userToken
	 *            identifying uniquely a user
	 * @param itemId
	 *            id of item
	 * @param preference
	 *            score
	 */
	void updatePreferences(String userToken, Long itemId, float preference);

	/**
	 * Finds user by the given token
	 * 
	 * @param userToken
	 *            to search for
	 * @return user or null if not found
	 */
	User findUser(String userToken);

}
