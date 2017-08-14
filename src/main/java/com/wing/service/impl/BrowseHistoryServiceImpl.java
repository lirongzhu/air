package com.wing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wing.dao.BrowseHistoryDao;
import com.wing.service.BrowseHistoryService;
import com.wing.bean.BrowseHistory;
import com.wing.bean.Customer;
import com.wing.bean.TourismItinerary;

@Service
public class BrowseHistoryServiceImpl implements BrowseHistoryService {

	@Autowired
	private BrowseHistoryDao browseHistoryDao;

	@Override
	public BrowseHistory findByCustomerAndTourismItinerary(Customer customer, TourismItinerary tourismItinerary) {
		return browseHistoryDao.findByCustomerAndTourismItinerary(customer, tourismItinerary);
	}

	@Override
	public Page<BrowseHistory> findByCustomer(Pageable page, Customer customer) {
		return browseHistoryDao.findByCustomer(page, customer);
	}

	@Override
	public void save(BrowseHistory browseHistory) {
		browseHistoryDao.save(browseHistory);
	}

}
