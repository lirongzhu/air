package com.wing.dao;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.TourismFeature;
import com.wing.bean.TourismItinerary;

public interface TourismFeatureDao extends BaseRepository<TourismFeature, Integer>{

	public TourismFeature findByTourismItinerary(TourismItinerary tourismItinerary);
}
