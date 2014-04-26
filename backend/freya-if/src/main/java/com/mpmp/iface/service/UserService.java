package com.mpmp.iface.service;

import java.util.Date;

import com.mpmp.iface.model.User;

public interface UserService {

	void updateLocation(String userId, double latitude, double longitude, Date time);
	
	User findUser(String userToken);
}
