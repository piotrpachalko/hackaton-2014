package com.mpmp.freya.service;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mpmp.iface.model.Preference;
import com.mpmp.iface.service.PreferenceDAO;

/**
 * Item DAO implementation
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
		em.persist(item);
	}

	@Override
	public List<Preference> findAll() {
		return em.createQuery("SELECT p FROM Preference p", Preference.class).getResultList();
	}

	@Override
	public Preference findById(Long id) {
		return em.find(Preference.class, id);
	}

}
