package com.mpmp.freya.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.mpmp.iface.model.Location;
import com.mpmp.iface.model.Preference;
import com.mpmp.iface.model.User;
import com.mpmp.iface.service.PreferenceDAO;
import com.mpmp.iface.service.UserDAO;
import com.mpmp.iface.service.UserService;

/**
 * User service implementation
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
@Stateless
@Remote(UserService.class)
public class UserServiceEJB implements UserService {

	@EJB
	private UserDAO userDAO;

	@EJB
	private PreferenceDAO preferenceDAO;

	@Override
	public void updateLocation(String userToken, double latitude, double longitude, Date time) {

		Location location = new Location(latitude, longitude, time);

		// TODO: side effect, move it to a separate method
		User user = userDAO.findByToken(userToken);
		if (user == null) {
			user = new User(userToken, location);
		}

		user.setLocation(location);
		userDAO.store(user);
	}

	@Override
	public User findUser(String userToken) {
		return userDAO.findByToken(userToken);
	}

	@Override
	public void updatePreferences(String userToken, Long itemId, float score) {

		User user = userDAO.findByToken(userToken);
		if (user != null) {
			Preference preference = preferenceDAO.findForUserByItemId(user.getId(), itemId);
			if (preference != null) {
				preference.setScore(score);
				preferenceDAO.store(preference);
			}
		}

	}
}
