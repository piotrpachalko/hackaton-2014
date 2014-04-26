package com.mpmp.iface.service;

import java.util.Date;

public interface UserService {

	void updateLocation(String userId, double latitude, double longitude, Date time);
}
