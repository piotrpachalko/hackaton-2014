package com.mpmp.iface.service;

import java.util.List;

import com.mpmp.iface.model.Item;

/**
 * Item data access object
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public interface ItemDAO {

	/**
	 * Stores item in storage
	 * 
	 * @param item
	 *            to store
	 */
	void store(Item item);

	/**
	 * Finds item by id
	 * 
	 * @param id
	 *            of item
	 * @return item or exception if not found
	 */
	Item findById(long id);

	/**
	 * Retrieves all item from storage
	 * 
	 * @return found items
	 */
	List<Item> findAll();
}
