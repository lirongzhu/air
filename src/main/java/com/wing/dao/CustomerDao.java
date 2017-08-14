package com.wing.dao;


import com.wing.utils.orm.BaseRepository;
import com.wing.bean.Customer;

public interface CustomerDao extends BaseRepository<Customer, Integer>{

	public Customer findByLoginName(String loginName);
}
