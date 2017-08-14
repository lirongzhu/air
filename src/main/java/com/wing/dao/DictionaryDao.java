package com.wing.dao;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.Dictionary;

public interface DictionaryDao extends BaseRepository<Dictionary, Integer>{

	public Dictionary findByDictionaryKey(String dictionaryKey);
}
