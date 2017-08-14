package com.wing.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wing.dao.CountITourismVisitorDao;
import com.wing.dao.CountRouteVisitorDao;
import com.wing.service.TaskJobService;
import com.wing.utils.cache.impl.CountITourismVisitorCache;
import com.wing.utils.cache.impl.CountRouteVisitorCache;
import com.wing.bean.CountITourismVisitor;
import com.wing.bean.CountRouteVisitor;

@Service
public class TaskJobServiceImpl implements TaskJobService {

	private static final Logger logger = LogManager.getLogger(TaskJobServiceImpl.class.getName());
	
	@Autowired
	private CountITourismVisitorDao countITourismVisitorDao;
	
	@Autowired
	private CountRouteVisitorDao countRouteVisitorDao;
	
	@Override
	@Scheduled(cron = "0 5 0/1 * * ?") 
	public void saveCountITourismVisitor() {

		logger.info("saveCountITourismVisitor 开始执行.......");
		
		CountITourismVisitorCache citvc = new CountITourismVisitorCache();
		
		if(citvc.get(getPreDate()) != null){
			
			CountITourismVisitor citv = new CountITourismVisitor();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");  
			Date nowDate = null;
			
			try {
				nowDate = sdf.parse(getPreDate());
			}catch (ParseException e) {
				logger.error(e);
			} 
			
			citv.setVisitDate(nowDate);
			citv.setCountNum(Integer.valueOf(citvc.get(getPreDate()).toString()));
			
			countITourismVisitorDao.save(citv);
		}
	}

	@Override
	@Scheduled(cron = "0 10 0/1 * * ?") 
	public void saveCountRouteVisitor() {

		logger.info("saveCountRouteVisitor 开始执行.......");
		
		CountRouteVisitorCache crvc = new CountRouteVisitorCache();
		
		if(crvc.get(getPreDate()) != null){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");  
			Date nowDate = null;
			
			try {
				nowDate = sdf.parse(getPreDate());
			}catch (ParseException e) {
				logger.error(e);
			} 
			
			Map<Integer, Integer> map = (Map<Integer, Integer>)crvc.get(getPreDate());
			Iterator<Entry<Integer, Integer>> it = map.entrySet().iterator();
			
			while(it.hasNext()){
				
				CountRouteVisitor crv = new CountRouteVisitor();
				Integer key = it.next().getKey();
				
				crv.setVisitDate(nowDate);
				crv.setItineraryId(key);
				crv.setCountNum(map.get(key));
				
				countRouteVisitorDao.save(crv);
			}
		}
	}
	
	public String getPreDate(){
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -1); //得到前一小时
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		
		return sdf.format(date);
	}
}
