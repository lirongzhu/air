package com.wing.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.wing.bean.Customer;

public interface CustomerService {
	
	public Page<Customer> findAll(Specification<Customer> spe, Pageable pageable);
	
	public Customer findOne(Integer id);
	
	public Customer save(Customer customer);
	
	public Customer findByLoginName(String loginName);
	
	public Customer reset(Integer resetId);
	
	public Customer disable(Integer disableId);
	
	public Customer update(Customer customer);
}
