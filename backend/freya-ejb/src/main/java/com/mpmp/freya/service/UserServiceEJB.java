package com.mpmp.freya.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.mpmp.iface.model.Location;
import com.mpmp.iface.model.User;
import com.mpmp.iface.service.UserDAO;
import com.mpmp.iface.service.UserService;

@Stateless
@Remote(UserService.class)
public class UserServiceEJB implements UserService {

	@EJB
	private UserDAO userDAO;

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

}
