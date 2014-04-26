package com.mpmp.iface.service;

import java.util.List;

public interface EntityDAO<T, K> {
	/**
	 * Stores T in storage
	 * 
	 * @param entity
	 *            to store
	 */
	void store(T entity);

	/**
	 * Finds entity by id
	 * 
	 * @param id
	 *            of entity
	 * @return entity or exception if not found
	 */
	T findById(K id);

	/**
	 * Retrieves all item from storage
	 * 
	 * @return found items
	 */
	List<T> findAll();
}
