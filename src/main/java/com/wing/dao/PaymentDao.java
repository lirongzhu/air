package com.wing.dao;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.Payment;

public interface PaymentDao extends BaseRepository<Payment,Integer>{
	
	public Payment findBySn(String sn);
}
