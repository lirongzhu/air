package com.wing.dao;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.Dictionary;
import com.wing.bean.DictionaryItem;

public interface DictionaryItemDao extends BaseRepository<DictionaryItem, Integer>{

	public List<DictionaryItem> findItemByDictionary(Dictionary dictionary, Sort sort);
	
	public List<DictionaryItem> findItemByFullName(String fullName, Sort sort);
}
