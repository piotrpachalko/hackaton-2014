package com.mpmp.iface.service;

import java.util.List;

import com.mpmp.iface.model.Item;

/**
 * Item Service interface
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public interface ItemService {

	/**
	 * @param size
	 *            number of items to get
	 * @param userId
	 *            id of user
	 * @return retrieved items
	 */
	List<Item> getItems(int size, String userId, double latitude, double longitude, long time);

	/**
	 * Updates item preferences
	 * 
	 * @param userToken
	 *            providing preferences
	 * @param itemId
	 *            id of item for which the preference is updated
	 * @param preference
	 *            score
	 */
	void updatePreferences(String userToken, Long itemId, float preference);

}
