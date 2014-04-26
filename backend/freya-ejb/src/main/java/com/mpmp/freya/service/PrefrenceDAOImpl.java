package com.mpmp.freya.service;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mpmp.iface.model.Preference;
import com.mpmp.iface.service.PreferenceDAO;

/**
 * Preference DAO implementation
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
@Stateless
@Remote(PreferenceDAO.class)
public class PrefrenceDAOImpl implements PreferenceDAO {

	@PersistenceContext(unitName = "freya")
	EntityManager em;

	@Override
	public void store(Preference item) {
		em.merge(item);
	}

	@Override
	public List<Preference> findAll() {
		return em.createQuery("SELECT p FROM Preference p", Preference.class).getResultList();
	}

	@Override
	public Preference findById(Long id) {
		return em.find(Preference.class, id);
	}

	@Override
	public Preference findForUserByItemId(Long userId, Long itemId) {

		TypedQuery<Preference> query = em.createQuery(
				"SELECT p FROM Preference p WHERE p.prefKey.itemId = :itemId AND p.prefKey.userId = :userId", Preference.class)
				.setParameter("itemId", itemId)
				.setParameter("userId", userId);

		List<Preference> results = query.getResultList();
		if (results.isEmpty() && results.size() != 1) {
			return null;
		} else {
			return results.get(0);
		}

	}

}
