package com.wing.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wing.common.constant.Constant;
import com.wing.common.enums.EnableStatus;
import com.wing.dao.InformationDao;
import com.wing.service.InformationService;
import com.wing.service.OperationLogService;
import com.wing.bean.Information;

@Service
public class InformationServiceImpl implements InformationService {

	private String operationMessage = "资讯管理 id=";
	
	@Autowired
	InformationDao informationDao;
	
	@Autowired
	private OperationLogService operationLogService;
	
	@Override
	public Page<Information> findAll(Specification<Information> spe,Pageable pageable) {
		return informationDao.findAll(spe,pageable);
	}

	@Override
	public Information findOne(Integer id) {
		return informationDao.findOne(id);
	}

	@Override
	public  List<Information> findByHotType(Integer hotType) {
		return informationDao.findByHotType(hotType);
	}

	@Override
	public void update(Information information) {

		informationDao.save(information);
		
		operationLogService.updateOperation(operationMessage + information.getInformationId());
	}

	@Override
	public void setHot(Integer infoId, Integer hotType) {
		
		if(hotType != 3){
			Information oldInfo = findByHotType(hotType).size() == 0 ? null : findByHotType(hotType).get(0) ;
			
			if(oldInfo != null){
				oldInfo.setHotType(null);
				this.save(oldInfo);
			}
		}
		
		Information info = this.findOne(infoId);
		info.setHotType(hotType);
		
		this.save(info);
		
		operationLogService.updateOperation("资讯管理 设置热门 id = " + info.getInformationId());
	}
	
	@Override
	public void cancelHot(Integer cancelId) {
		
		Information info = this.findOne(cancelId);
		info.setHotType(null);
		
		this.save(info);
		
		operationLogService.updateOperation("资讯管理 取消热门 id = " + info.getInformationId());
	}
	
	@Override
	public void save(Information information) {
		
		information.setCreateDate(new Date());
		information.setCreateUser(Constant.getCurrentUser());
		information.setInformationState(EnableStatus.正常);
		
		informationDao.save(information);
		
		operationLogService.createOperation(operationMessage + information.getInformationId());
	}

	@Override
	public void delete(Integer deleteId) {
		operationLogService.deleteOperation(operationMessage + deleteId);
		informationDao.delete(deleteId);
	}
}
