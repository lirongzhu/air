package com.wing.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.BrowseHistory;
import com.wing.bean.Customer;
import com.wing.bean.TourismItinerary;

public interface BrowseHistoryDao extends BaseRepository<BrowseHistory, Integer>{

	public BrowseHistory findByCustomerAndTourismItinerary(Customer customer, TourismItinerary tourismItinerary);
	
	public Page<BrowseHistory> findByCustomer(Pageable page, Customer customer);
}
