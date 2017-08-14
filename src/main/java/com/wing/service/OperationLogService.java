package com.wing.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wing.bean.OperationLog;
import com.wing.bean.User;

public interface OperationLogService {

	public Page<OperationLog> findByLoginUser(Pageable page, User user);
	
	public void save(OperationLog operationLog);
	
	public void createOperation(String content);
	
	public void updateOperation(String content);
	
	public void deleteOperation(String content);
	
	public void disableOperation(String content);
	
	public void enableOperation(String content);
}
