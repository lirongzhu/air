package com.wing.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.wing.bean.Information;

public interface InformationService {
	
	public Page<Information> findAll(Specification<Information> spe,Pageable pageable);
	
	public Information findOne(Integer id);

	public void save(Information information);
	
	public void update(Information information);
	
	public void delete(Integer deleteId);
	
	public void setHot(Integer infoId, Integer hotType);
	
	public List<Information> findByHotType(Integer hotType);
	
	public void cancelHot(Integer cancelId);
}
