package com.mpmp.freya.service;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
		em.merge(user);
	}

	@Override
	public List<User> findAll() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	/**
	 * {@inheritDoc} Returns null if not found
	 */
	@Override
	public User findById(Long id) {
		return em.find(User.class, id);
	}

	@Override
	public User findByToken(String userToken) {
		
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userToken = :token", User.class)
				.setParameter("token", userToken);
		
		List<User> results = query.getResultList();
		if (results.isEmpty() && results.size() != 1) {
			return null;
		} else {
			return results.get(0);
		}

	}
}
