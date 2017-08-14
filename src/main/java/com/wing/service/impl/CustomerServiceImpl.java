package com.wing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wing.common.enums.EnableStatus;
import com.wing.dao.CustomerDao;
import com.wing.service.CustomerService;
import com.wing.service.OperationLogService;
import com.wing.bean.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	private String operationMessage = "用户管理 id=";
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private OperationLogService operationLogService;
	
	@Override
	public Page<Customer> findAll(Specification<Customer> spe, Pageable pageable) {
		return customerDao.findAll(spe, pageable);
	}

	@Override
	public Customer findOne(Integer id) {
		return customerDao.findOne(id);
	}

	@Override
	public Customer findByLoginName(String loginName) {
		return customerDao.findByLoginName(loginName);
	}

	@Override
	public Customer save(Customer customer) {
		
		customerDao.save(customer);
		operationLogService.createOperation(operationMessage + customer.getId());
		
		return customer;
	}
	
	@Override
	public Customer update(Customer customer) {
		
		operationLogService.updateOperation(operationMessage + customer.getId());
		return customerDao.save(customer);
	}
	
	@Override
	public Customer reset(Integer resetId) {
		
		Customer customer = findOne(resetId);
		customer.setState(EnableStatus.正常);
		
		customerDao.save(customer);
		
		operationLogService.enableOperation(operationMessage + customer.getId());
		return customer;
	}

	@Override
	public Customer disable(Integer disableId) {

		Customer customer = findOne(disableId);
		customer.setState(EnableStatus.停用);
		
		customerDao.save(customer);
		
		operationLogService.disableOperation(operationMessage + customer.getId());
		return customer;
	}
}
