package com.wing.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wing.common.constant.Constant;
import com.wing.common.enums.EnableStatus;
import com.wing.dao.DictionaryDao;
import com.wing.dao.DictionaryItemDao;
import com.wing.service.DictionaryService;
import com.wing.service.OperationLogService;
import com.wing.bean.Dictionary;
import com.wing.bean.DictionaryItem;

@Service
public class DictionaryServiceImpl implements DictionaryService {


	private String operationMessage = "字典以及子项管理 id=";
	
	@Autowired
	private OperationLogService operationLogService;
	
	@Autowired
	DictionaryDao dictionaryDao;
	
	@Autowired
	DictionaryItemDao dictionaryItemDao;

	@Override
	public Page<Dictionary> findAll(Specification<Dictionary> spe, Pageable pageable) {
		return dictionaryDao.findAll(spe, pageable);
	}

	@Override
	public Page<DictionaryItem> findAllItem(Specification<DictionaryItem> spe, Pageable pageable) {
		return dictionaryItemDao.findAll(spe, pageable);
	}
	
	@Override
	public Dictionary findOne(Integer id) {
		return dictionaryDao.findOne(id);
	}

	@Override
	public void save(Dictionary dictionary) {
		
		dictionary.setCreateDate(new Date());
		dictionary.setEnable(EnableStatus.正常);
		dictionary.setCreateUser(Constant.getCurrentUser());
		dictionaryDao.save(dictionary);
		
		operationLogService.createOperation(operationMessage + dictionary.getDictionaryId());
	}

	@Override
	public void update(Dictionary dictionary) {
		dictionaryDao.save(dictionary);
		
		operationLogService.updateOperation(operationMessage + dictionary.getDictionaryId());
	}

	@Override
	public void enable(Integer id) {
		
		Dictionary dictionary = findOne(id);
		dictionary.setEnable(EnableStatus.停用);
		
		dictionaryDao.save(dictionary);
		
		operationLogService.disableOperation(operationMessage + dictionary.getDictionaryId());
	}
	
	@Override
	public void reset(Integer id) {
		
		Dictionary dictionary = findOne(id);
		dictionary.setEnable(EnableStatus.正常);
		
		dictionaryDao.save(dictionary);
		
		operationLogService.enableOperation(operationMessage + dictionary.getDictionaryId());
	}

	@Override
	public List<DictionaryItem> findItemByDictionary(Dictionary dictionary, Sort sort) {
		return dictionaryItemDao.findItemByDictionary(dictionary, sort);
	}

	@Override
	public void saveItem(DictionaryItem dictionaryItem) {
		
		dictionaryItem.setCreateDate(new Date());
		dictionaryItem.setCreateUser(Constant.getCurrentUser());
		dictionaryItem.setEnable(EnableStatus.正常);
		dictionaryItemDao.save(dictionaryItem);
		
		operationLogService.createOperation(operationMessage + dictionaryItem.getItemId());
	}

	@Override
	public void updateItem(DictionaryItem dictionaryItem) {
		dictionaryItemDao.save(dictionaryItem);
		
		operationLogService.updateOperation(operationMessage + dictionaryItem.getItemId());
	}

	@Override
	public void enableItem(Integer id) {

		DictionaryItem dictionaryItem = dictionaryItemDao.findOne(id);
		dictionaryItem.setEnable(EnableStatus.停用);
		
		dictionaryItemDao.save(dictionaryItem);
		
		operationLogService.disableOperation(operationMessage + dictionaryItem.getItemId());
	}

	@Override
	public DictionaryItem findItemOne(Integer id) {
		return dictionaryItemDao.findOne(id);
	}

	@Override
	public void resetItem(Integer id) {
		
		DictionaryItem dictionaryItem = dictionaryItemDao.findOne(id);
		dictionaryItem.setEnable(EnableStatus.正常);
		
		dictionaryItemDao.save(dictionaryItem);
		
		operationLogService.enableOperation(operationMessage + dictionaryItem.getItemId());
	}
}
