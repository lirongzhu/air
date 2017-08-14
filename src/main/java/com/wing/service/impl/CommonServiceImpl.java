package com.wing.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wing.common.enums.DictionaryType;
import com.wing.common.enums.EnableStatus;
import com.wing.dao.DictionaryDao;
import com.wing.dao.DictionaryItemDao;
import com.wing.service.CommonService;
import com.wing.bean.Dictionary;
import com.wing.bean.DictionaryItem;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Autowired
	private DictionaryItemDao dictionaryItemDao;
	
	@Override
	public Dictionary findDictionaryById(EnableStatus enableStatus, Integer id) {
		
		final Specification<Dictionary> spe = new Specification<Dictionary>() {

			@Override
			public Predicate toPredicate(Root<Dictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(enableStatus != null){
					predicates.add(cb.equal(root.get("enable").as(EnableStatus.class), enableStatus));
				}
				
				predicates.add(cb.equal(root.get("dictionaryId").as(Integer.class), id));
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		return dictionaryDao.findOne(spe);
	}

	@Override
	public DictionaryItem findDictionaryItemById(EnableStatus enableStatus, Integer id) {

		final Specification<DictionaryItem> spe = new Specification<DictionaryItem>() {

			@Override
			public Predicate toPredicate(Root<DictionaryItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(enableStatus != null){
					predicates.add(cb.equal(root.get("enable").as(EnableStatus.class), enableStatus));
				}
				
				predicates.add(cb.equal(root.get("itemId").as(Integer.class), id));
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		return dictionaryItemDao.findOne(spe);
	}

	@Override
	public List<DictionaryItem> findItemByDictionary(EnableStatus enableStatus, Dictionary dictionary) {
		
		final Specification<DictionaryItem> spe = new Specification<DictionaryItem>() {

			@Override
			public Predicate toPredicate(Root<DictionaryItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(enableStatus != null){
					predicates.add(cb.equal(root.get("enable").as(EnableStatus.class), enableStatus));
				}
				
				predicates.add(cb.equal(root.get("dictionary").as(Dictionary.class), dictionary));
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		Sort sort = new Sort(Direction.ASC, "itemSort");
		
		return dictionaryItemDao.findAll(spe, sort);
	}

	@Override
	public List<DictionaryItem> findItemByDictionaryKey(EnableStatus enableStatus, String dictionaryKey) {
		
		final Specification<DictionaryItem> spe = new Specification<DictionaryItem>() {

			@Override
			public Predicate toPredicate(Root<DictionaryItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(enableStatus != null){
					predicates.add(cb.equal(root.get("enable").as(EnableStatus.class), enableStatus));
				}
				
				predicates.add(cb.equal(root.get("dictionary").get("dictionaryKey").as(String.class), dictionaryKey));
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		Sort sort = new Sort(Direction.ASC, "itemSort");
		
		return dictionaryItemDao.findAll(spe, sort);
	}

	@Override
	public DictionaryItem findItemByItemKey(EnableStatus enableStatus, String itemKey) {
		
		final Specification<DictionaryItem> spe = new Specification<DictionaryItem>() {

			@Override
			public Predicate toPredicate(Root<DictionaryItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(enableStatus != null){
					predicates.add(cb.equal(root.get("enable").as(EnableStatus.class), enableStatus));
				}
				
				predicates.add(cb.equal(root.get("itemKey").as(String.class), itemKey));
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		return dictionaryItemDao.findOne(spe);
	}

	@Override
	public List<Dictionary> findAllDictionary(EnableStatus enableStatus, DictionaryType dictionaryType) {

		final Specification<Dictionary> spe = new Specification<Dictionary>() {

			@Override
			public Predicate toPredicate(Root<Dictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(enableStatus != null){
					predicates.add(cb.equal(root.get("enable").as(EnableStatus.class), enableStatus));
				}
				
				if(dictionaryType != null){
					predicates.add(cb.equal(root.get("dictionaryType").as(DictionaryType.class), dictionaryType));
				}
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		return dictionaryDao.findAll(spe);
	}

	@Override
	public List<DictionaryItem> findItemByFullName(EnableStatus enableStatus, String fullName) {
		return dictionaryItemDao.findItemByFullName(fullName, null);
	}

	@Override
	public Dictionary findDictionaryByKey(EnableStatus enableStatus, String key) {
		return dictionaryDao.findByDictionaryKey(key);
	}

}
