package com.wing.dao;

import java.util.List;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.Information;

public interface InformationDao extends BaseRepository<Information, Integer>{

	public  List<Information> findByHotType(Integer hotType);
}
