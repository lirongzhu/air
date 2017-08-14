package com.wing.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wing.common.enums.EnableStatus;
import com.wing.common.enums.UserType;
import com.wing.dao.UserDao;
import com.wing.service.OperationLogService;
import com.wing.service.UserService;
import com.wing.bean.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	private String operationMessage = "用户管理 管理 id=";
	
	@Autowired
	private OperationLogService operationLogService;
	
	public Page<User> findAll(Specification<User> spe,Pageable pageable){
		return userDao.findAll(spe,pageable);
	}

	public User findOne(Integer id) {
		return userDao.findOne(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public void update(User user) {
		
		userDao.save(user);
		operationLogService.updateOperation(operationMessage + user.getId());
	}
	
	public void save(User user) {
		
		user.setCreateDate(new Date());
		user.setUserStatus(EnableStatus.正常);
		
		userDao.save(user);
		operationLogService.createOperation(operationMessage + user.getId());
	}

	@Override
	public void reset(Integer resetId) {
		User user = this.findOne(resetId);
		user.setUserStatus(EnableStatus.正常);
		
		userDao.save(user);
		
		operationLogService.enableOperation(operationMessage + user.getId());
		
	}

	@Override
	public void disable(Integer disableId) {
		
		User user = this.findOne(disableId);
		user.setUserStatus(EnableStatus.停用);
		
		userDao.save(user);
		
		operationLogService.disableOperation(operationMessage + user.getId());
		
	}

	@Override
	public List<User> findByUserType(UserType userType) {
		return userDao.findByUserType(userType);
	}
}
