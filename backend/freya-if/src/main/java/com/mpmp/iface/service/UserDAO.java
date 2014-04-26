package com.mpmp.iface.service;

import com.mpmp.iface.model.User;

/**
 * User data access object
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public interface UserDAO extends EntityDAO<User, Long> {

	/**
	 * Finds user by the provided token
	 * 
	 * @param userToken
	 *            to search user for
	 * @return user if found or null otherwise
	 */
	User findByToken(String userToken);
}
