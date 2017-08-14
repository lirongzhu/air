package com.wing.dao;

import java.util.List;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.TourismCostStatement;
import com.wing.bean.TourismItinerary;

public interface TourismCostStatementDao extends BaseRepository<TourismCostStatement, Integer>{

	public List<TourismCostStatement> findByTourismItinerary(TourismItinerary tourismItinerary);
}
