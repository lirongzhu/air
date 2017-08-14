package com.wing.dao;

import java.util.List;

import com.wing.common.enums.UserType;
import com.wing.utils.orm.BaseRepository;
import com.wing.bean.User;

public interface UserDao extends BaseRepository<User, Integer>{

	public User findByUsername(String username);
	
	public List<User> findByUserType(UserType userType);
}
