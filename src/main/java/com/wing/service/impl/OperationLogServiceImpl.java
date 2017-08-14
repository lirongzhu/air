package com.wing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wing.common.enums.OperationType;
import com.wing.dao.OperationLogDao;
import com.wing.service.OperationLogService;
import com.wing.bean.OperationLog;
import com.wing.bean.User;

@Service
public class OperationLogServiceImpl implements OperationLogService {

	@Autowired
	private OperationLogDao operationLogDao;

	@Override
	public Page<OperationLog> findByLoginUser(Pageable page, User user) {
		return operationLogDao.findByOperationUser(page, user);
	}

	@Override
	public void createOperation(String content) {
		OperationLog operationLog = new OperationLog(OperationType.新增, content);
		operationLogDao.save(operationLog);
	}

	@Override
	public void updateOperation(String content) {
		OperationLog operationLog = new OperationLog(OperationType.修改, content);
		operationLogDao.save(operationLog);
	}

	@Override
	public void deleteOperation(String content) {
		OperationLog operationLog = new OperationLog(OperationType.删除, content);
		operationLogDao.save(operationLog);
	}

	@Override
	public void disableOperation(String content) {
		OperationLog operationLog = new OperationLog(OperationType.停用, content);
		operationLogDao.save(operationLog);
	}

	@Override
	public void enableOperation(String content) {
		OperationLog operationLog = new OperationLog(OperationType.启用, content);
		operationLogDao.save(operationLog);
	}

	@Override
	public void save(OperationLog operationLog) {
		operationLogDao.save(operationLog);
	}
}
