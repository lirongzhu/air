package com.wing.service;

import java.util.List;

import com.wing.common.enums.DictionaryType;
import com.wing.common.enums.EnableStatus;
import com.wing.bean.Dictionary;
import com.wing.bean.DictionaryItem;

public interface CommonService {

	public Dictionary findDictionaryById(EnableStatus enableStatus, Integer id);
	
	public Dictionary findDictionaryByKey(EnableStatus enableStatus, String key);
	
	public DictionaryItem findDictionaryItemById(EnableStatus enableStatus, Integer id);
	
	public List<DictionaryItem> findItemByDictionary(EnableStatus enableStatus, Dictionary dictionary);
	
	public List<DictionaryItem> findItemByDictionaryKey(EnableStatus enableStatus, String dictionaryKey);
	
	public DictionaryItem findItemByItemKey(EnableStatus enableStatus, String itemKey);
	
	public List<Dictionary> findAllDictionary(EnableStatus enableStatus, DictionaryType dictionaryType);
	
	public List<DictionaryItem> findItemByFullName(EnableStatus enableStatus, String fullName);
}
