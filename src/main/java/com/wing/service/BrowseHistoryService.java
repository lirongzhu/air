package com.wing.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wing.bean.BrowseHistory;
import com.wing.bean.Customer;
import com.wing.bean.TourismItinerary;

public interface BrowseHistoryService {
	
	public BrowseHistory findByCustomerAndTourismItinerary(Customer customer, TourismItinerary tourismItinerary);
	
	public Page<BrowseHistory> findByCustomer(Pageable page, Customer customer);
	
	public void save(BrowseHistory browseHistory);
}
