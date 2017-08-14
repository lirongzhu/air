package com.wing.shrio.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.wing.bean.User;
import com.wing.common.enums.EnableStatus;
import com.wing.dao.UserDao;
import com.wing.repository.UserRepository;

public class AuthcRealm extends AuthorizingRealm {

	@Autowired
	UserDao userDao;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		User user = (User)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();  
		userRepository.getList();
		info.addStringPermission(user.getPermissions());
		info.addRole(user.getRole());
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		// 把token转换成User对象  
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;  
        
        // 验证用户是否可以登录  
        User user = userDao.findByUsername(userToken.getUsername());  
        if(user == null) return null; // 异常处理，找不到数据  
        
        if(user.getUserStatus() == EnableStatus.停用) return null;
        
        //当前 Realm 的 name  
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());  
	}
}
