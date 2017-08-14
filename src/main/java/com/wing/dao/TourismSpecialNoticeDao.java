package com.wing.dao;

import java.util.List;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.TourismItinerary;
import com.wing.bean.TourismSpecialNotice;

public interface TourismSpecialNoticeDao extends BaseRepository<TourismSpecialNotice, Integer>{

	public List<TourismSpecialNotice> findByTourismItinerary(TourismItinerary tourismItinerary);
}
