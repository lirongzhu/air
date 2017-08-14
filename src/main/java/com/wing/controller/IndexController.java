package com.wing.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wing.common.constant.Constant;
import com.wing.common.enums.UserType;
import com.wing.utils.util.PropertyUtil;

@Controller
public class IndexController {

	@RequestMapping(value= "login.do", method=RequestMethod.GET)
	public String log(HttpServletRequest request, HttpServletResponse  response){
    	return "login";
	}
	
	@RequestMapping(value= "login.do", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse  response, ModelMap model){
		
		UsernamePasswordToken token = new UsernamePasswordToken(request.getParameter("username"), request.getParameter("password"));
		 
        try {
        	Subject subject = SecurityUtils.getSubject();  
            subject.login(token);
            subject.getSession().setTimeout(1000 * 60 * 60 * 12);
            
            return "index";
        } catch (AuthenticationException e) {
        	e.printStackTrace();
        	model.put(Constant.DANGER_MESSAGE, "用户名或密码错误！");
        	return "login";
        }
	}
	
	@RequestMapping(value= "relogin.do", method=RequestMethod.GET)
	public String relogin(HttpServletRequest request, HttpServletResponse  response){
    	return "relogin";
	}
	
	@RequestMapping(value= "logout.do", method=RequestMethod.GET)
	public String logout(){
		
    	Subject subject = SecurityUtils.getSubject();  
        subject.logout();
        
        return "login";
	}
	
	@RequestMapping(value= "changeInfo.do", method=RequestMethod.GET)
	public String changeInfo(Model modelMap){
		return "redirect:welcome/updateInfo.do "; 
	}
}
