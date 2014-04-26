package com.mpmp.iface.service;

import com.mpmp.iface.model.User;

public interface UserDAO extends EntityDAO<User, Long> {

	User findByToken(String userToken);
}
