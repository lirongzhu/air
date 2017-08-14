package com.wing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wing.dao.PaymentDao;
import com.wing.service.OperationLogService;
import com.wing.service.PaymentService;
import com.wing.bean.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	private String operationMessage = "订单支付管理 id=";
	
	@Autowired
	private OperationLogService operationLogService;
	
	@Autowired
	private PaymentDao paymentDao;

	@Override
	public Page<Payment> findAll(Specification<Payment> spe, Pageable pageable) {
		return paymentDao.findAll(spe,pageable);
	}

	@Override
	public Payment findOne(Integer id) {
		return paymentDao.findOne(id);
	}

	@Override
	public void save(Payment payment) {
		paymentDao.save(payment);
		
		operationLogService.createOperation(operationMessage + payment.getPaymentId());
	}

	@Override
	public Payment findBySn(String sn) {
		return paymentDao.findBySn(sn);
	}
}
