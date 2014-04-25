package com.mpmp.freya.service;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;

/**
 * Item DAO implementation
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
@Stateless
@Remote(ItemDAO.class)
public class ItemDAOImpl implements ItemDAO {

	@PersistenceContext(unitName = "freya")
	EntityManager em;

	@Override
	public void store(Item item) {
		em.persist(item);
	}

	@Override
	public List<Item> findAll() {
		return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
	}

	@Override
	public Item findById(long id) {
		return em.find(Item.class, id);
	}

}
