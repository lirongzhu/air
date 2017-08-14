package com.wing.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wing.common.constant.Constant;
import com.wing.common.enums.EnableStatus;
import com.wing.dao.TourismCostStatementDao;
import com.wing.dao.TourismFeatureDao;
import com.wing.dao.TourismItineraryDao;
import com.wing.dao.TourismSchedulingDao;
import com.wing.dao.TourismSpecialNoticeDao;
import com.wing.service.CommonService;
import com.wing.service.OperationLogService;
import com.wing.service.TourismService;
import com.wing.bean.TourismCostStatement;
import com.wing.bean.TourismFeature;
import com.wing.bean.TourismItinerary;
import com.wing.bean.TourismScheduling;
import com.wing.bean.TourismSpecialNotice;

@Service
public class TourismServiceImpl implements TourismService {
	
	@Autowired
	TourismItineraryDao tourismItineraryDao;
	
	@Autowired
	TourismFeatureDao tourismFeatureDao;
	
	@Autowired
	TourismCostStatementDao tourismCostStatementDao;
	
	@Autowired
	TourismSchedulingDao tourismSchedulingDao;
	
	@Autowired
	TourismSpecialNoticeDao tourismSpecialNoticeDao;
	
	@Autowired
	private CommonService commonService;
	
	private String operationMessage = "旅行线路 管理 id=";
	
	@Autowired
	private OperationLogService operationLogService;
	
	@Override
	@Transactional(readOnly = true)
	public Page<TourismItinerary> findAll(Pageable pageable, Specification<TourismItinerary> spe) {
		return tourismItineraryDao.findAll(spe, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public TourismItinerary findItineraryById(Integer id) {
		return tourismItineraryDao.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public TourismFeature findFeatureByItinerary(TourismItinerary tourismItinerary) {
		return tourismFeatureDao.findByTourismItinerary(tourismItinerary);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TourismCostStatement> findCostStatementByItinerary(TourismItinerary tourismItinerary) {
		return tourismCostStatementDao.findByTourismItinerary(tourismItinerary);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TourismScheduling> findSchedulingByItinerary(TourismItinerary tourismItinerary) {
		return tourismSchedulingDao.findByTourismItinerary(tourismItinerary, new Sort(Direction.ASC, "days"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<TourismSpecialNotice> findSpecialNoticeByItinerary(TourismItinerary tourismItinerary) {
		return tourismSpecialNoticeDao.findByTourismItinerary(tourismItinerary);
	}

	@Override
	public void save(TourismItinerary itinerary) {
		itinerary.setHasDelete(0);
		tourismItineraryDao.save(itinerary);
		
		operationLogService.createOperation(operationMessage + itinerary.getItineraryId());
	}

	@Override
	public void deleteSpecialNotice(Integer id) {
		tourismSpecialNoticeDao.delete(id);
	}

	@Override
	public void deleteCostStatement(Integer id) {
		tourismCostStatementDao.delete(id);
	}

	@Override
	public void deleteScheduling(Integer id) {
		tourismSchedulingDao.delete(id);
	}

	@Override
	public void updateAllTourismInfo(TourismItinerary itinerary) {

		update(itinerary);
		
		TourismFeature tourismFeature = itinerary.getTourismFeature();
		tourismFeature.setTourismItinerary(itinerary);
		tourismFeatureDao.save(tourismFeature);
		
		List<TourismCostStatement> costStatementList = itinerary.getCostStatementList();
		
		if(costStatementList != null){
			for(TourismCostStatement tcs : costStatementList){
				if(tcs != null){
					if(StringUtils.isNotBlank(tcs.getCostStatement())){
						tcs.setTourismItinerary(itinerary);
						tourismCostStatementDao.save(tcs);
					}
				}
			}
		}
		
		List<TourismScheduling> schedulingList = itinerary.getSchedulingList();
		
		if(schedulingList != null){
			for(TourismScheduling ts : schedulingList){
				if(ts != null){
					if(!(StringUtils.isBlank(ts.getAttractions()) && ts.getDays() == null 
							&& StringUtils.isBlank(ts.getRepast())  && StringUtils.isBlank(ts.getStay()) 
							&& StringUtils.isBlank(ts.getTraffic()))){
						
						ts.setTourismItinerary(itinerary);
						tourismSchedulingDao.save(ts);
					}
				}
			}
		}
		
		List<TourismSpecialNotice> specialNoticeList = itinerary.getSpecialNoticeList();
		
		if(specialNoticeList != null){
			for(TourismSpecialNotice tsn : specialNoticeList){
				if(tsn != null){
					if(StringUtils.isNotBlank(tsn.getSpecialNotice())){
						tsn.setTourismItinerary(itinerary);
						tourismSpecialNoticeDao.save(tsn);
					}
				}
			}
		}
	}

	@Override
	public void update(TourismItinerary itinerary) {
		
		itinerary.setHasDelete(0);
		tourismItineraryDao.save(itinerary);
		operationLogService.updateOperation(operationMessage + itinerary.getItineraryId());
	}

	@Override
	public void enable(Integer enableId) {
		TourismItinerary ti = this.findItineraryById(enableId);
		ti.setEnable(EnableStatus.停用);
		tourismItineraryDao.save(ti);
		
		operationLogService.disableOperation(operationMessage + enableId);
	}
	
	@Override
	public void delete(Integer deleteId) {
		TourismItinerary ti = this.findItineraryById(deleteId);
		ti.setHasDelete(1);
		tourismItineraryDao.save(ti);
		
		operationLogService.deleteOperation(operationMessage + deleteId);
	}
	
	@Override
	public void saveAllTourismInfo(TourismItinerary itinerary) {
		
		itinerary.setCreateDate(new Date());
		itinerary.setCreateUser(Constant.getCurrentUser());
		itinerary.setEnable(EnableStatus.正常);
		save(itinerary);
		
		TourismFeature tourismFeature = itinerary.getTourismFeature();
		tourismFeature.setTourismItinerary(itinerary);
		tourismFeatureDao.save(tourismFeature);
		
		List<TourismCostStatement> costStatementList = itinerary.getCostStatementList();
		
		if(costStatementList != null){
			for(TourismCostStatement tcs : costStatementList){
				if(tcs != null){
					if(StringUtils.isNotBlank(tcs.getCostStatement())){
						tcs.setTourismItinerary(itinerary);
						tourismCostStatementDao.save(tcs);
					}
				}
			}
		}
		
		List<TourismScheduling> schedulingList = itinerary.getSchedulingList();
		
		if(schedulingList != null){
			for(TourismScheduling ts : schedulingList){
				if(ts != null){
					if(!(StringUtils.isBlank(ts.getAttractions()) && ts.getDays() != null
							&& StringUtils.isBlank(ts.getRepast())  && StringUtils.isBlank(ts.getStay()) 
							&& StringUtils.isBlank(ts.getTraffic()))){
						
						ts.setTourismItinerary(itinerary);
						tourismSchedulingDao.save(ts);
					}
				}
			}
		}
		
		List<TourismSpecialNotice> specialNoticeList = itinerary.getSpecialNoticeList();
		
		if(specialNoticeList != null){
			for(TourismSpecialNotice tsn : specialNoticeList){
				if(tsn != null){
					if(StringUtils.isNotBlank(tsn.getSpecialNotice())){
						tsn.setTourismItinerary(itinerary);
						tourismSpecialNoticeDao.save(tsn);
					}
				}
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public TourismItinerary findById(Integer id) {
		return tourismItineraryDao.findOne(id);
	}
}
