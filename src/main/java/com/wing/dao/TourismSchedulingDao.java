package com.wing.dao;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.TourismItinerary;
import com.wing.bean.TourismScheduling;

public interface TourismSchedulingDao extends BaseRepository<TourismScheduling, Integer>{

	public List<TourismScheduling> findByTourismItinerary(TourismItinerary tourismItinerary, Sort sort);
}

