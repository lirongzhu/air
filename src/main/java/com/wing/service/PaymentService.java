package com.wing.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.wing.bean.Payment;

public interface PaymentService {

	public Page<Payment> findAll(Specification<Payment> spe, Pageable pageable); 
	
	public Payment findOne(Integer id);
	
	public void save(Payment payment);
	
	public Payment findBySn(String sn);
	
}
