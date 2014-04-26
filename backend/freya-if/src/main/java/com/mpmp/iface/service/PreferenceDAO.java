package com.mpmp.iface.service;

import com.mpmp.iface.model.Preference;

/**
 * Preference data access object
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public interface PreferenceDAO extends EntityDAO<Preference, Long> {

	/**
	 * Finds prefernce for the given user by the provided item id
	 * 
	 * @param userId
	 *            to search for
	 * @param itemId
	 *            to search for
	 * @return preference if found or null if non available
	 */
	Preference findForUserByItemId(Long userId, Long itemId);
}
