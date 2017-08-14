package com.wing.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.wing.common.enums.UserType;
import com.wing.bean.User;

public interface UserService {

	public Page<User> findAll(Specification<User> spe,Pageable pageable);
	
	public List<User> findAll();
	
	public User findOne(Integer id);
	
	public User findByUsername(String username);
	
	public List<User> findByUserType(UserType userType);
	
	public void save(User user);
	
	public void update(User user);
	
	public void reset(Integer resetId);
	
	public void disable(Integer disableId);
}
