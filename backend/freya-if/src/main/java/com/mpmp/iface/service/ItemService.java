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
	List<Item> getItems(int size, String userId);
	
}
