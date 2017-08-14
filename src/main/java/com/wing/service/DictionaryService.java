package com.wing.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.wing.bean.Dictionary;
import com.wing.bean.DictionaryItem;

/**
 * @author lirongzhu
 * 除非字典功能的增删改查，其他方法不能调取此service，移步CommonService中调取
 *
 */
public interface DictionaryService {
	
	public Page<Dictionary> findAll(Specification<Dictionary> spe, Pageable pageable);
	
	public Page<DictionaryItem> findAllItem(Specification<DictionaryItem> spe, Pageable pageable);
	
	public Dictionary findOne(Integer id);
	
	public DictionaryItem findItemOne(Integer id);
	
	public void save(Dictionary dictionary);
	
	public void update(Dictionary dictionary);
	
	public void enable(Integer id);
	
	public void reset(Integer id);
	
	public List<DictionaryItem> findItemByDictionary(Dictionary dictionary, Sort sort);
	
	public void saveItem(DictionaryItem dictionaryItem);
	
	public void updateItem(DictionaryItem dictionaryItem);
	
	public void enableItem(Integer id);
	
	public void resetItem(Integer id);
}
