package com.wing.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.wing.bean.TourismCostStatement;
import com.wing.bean.TourismFeature;
import com.wing.bean.TourismItinerary;
import com.wing.bean.TourismScheduling;
import com.wing.bean.TourismSpecialNotice;

public interface TourismService {
	
	public Page<TourismItinerary> findAll(Pageable pageable, Specification<TourismItinerary> spe);
	
	public TourismItinerary findItineraryById(Integer id);
	
	public TourismFeature findFeatureByItinerary(TourismItinerary tourismItinerary);

	public List<TourismCostStatement> findCostStatementByItinerary(TourismItinerary tourismItinerary);
	
	public List<TourismScheduling> findSchedulingByItinerary(TourismItinerary tourismItinerary);
	
	public List<TourismSpecialNotice> findSpecialNoticeByItinerary(TourismItinerary tourismItinerary);
	
	public void delete(Integer deleteId);
	
	public void deleteSpecialNotice(Integer id);
	
	public void deleteCostStatement(Integer id);
	
	public void deleteScheduling(Integer id);
	
	public void saveAllTourismInfo(TourismItinerary itinerary);
	
	public void updateAllTourismInfo(TourismItinerary itinerary);
	
	public void save(TourismItinerary itinerary);
	
	public void update(TourismItinerary itinerary);
	
	public void enable(Integer enableId);
	
	public TourismItinerary findById(Integer id);
	
}
