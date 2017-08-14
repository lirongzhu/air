package com.wing.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.OperationLog;
import com.wing.bean.User;

public interface OperationLogDao extends BaseRepository<OperationLog, Integer>{

	public Page<OperationLog> findByOperationUser(Pageable page, User user);
}
