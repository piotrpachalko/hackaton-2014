package com.mpmp.freya.service;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mpmp.iface.model.User;
import com.mpmp.iface.service.UserDAO;

/**
 * User DAO implementation
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
@Stateless
@Remote(UserDAO.class)
public class UserDAOImpl implements UserDAO {

	@PersistenceContext(unitName = "freya")
	EntityManager em;

	@Override
	public void store(User user) {
		em.persist(user);
	}

	@Override
	public List<User> findAll() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	@Override
	public User findById(String id) {
		return em.find(User.class, id);
	}
}
