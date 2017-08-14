package com.wing.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wing.bean.User;
import com.wing.utils.orm.BaseHibernateTemplateDao;

@Repository
public class UserRepository extends BaseHibernateTemplateDao {

	public List<User> getList(){
		return (List<User>)getHibernateTemplate().find(" from User");
		//return getJdbcTemplate().query("select * from crm_user", new Object[]{}, new BeanPropertyRowMapper<User>(User.class));
	}
}
