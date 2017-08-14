package com.wing.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.common.constant.Constant;
import com.wing.common.enums.UserType;
import com.wing.service.UserService;
import com.wing.bean.User;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("list.do")
	public String accounts(Model modelMap, Pageable pageable,HttpServletRequest request){
		
		final Specification<User> spe = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(StringUtils.isNotBlank(request.getParameter("serach_content"))){
					
					predicates.add(
						cb.or(
							cb.like(root.get("username").as(String.class), "%" + request.getParameter("serach_content") + "%"), 
							cb.or(
								cb.like(root.get("contactName").as(String.class), "%" + request.getParameter("serach_content") + "%"),
								cb.like(root.get("contactPhone").as(String.class), "%" + request.getParameter("serach_content") + "%"))));
				}
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		modelMap.addAttribute("pagedList", userService.findAll(spe,pageable));
		return "accounts/list";
	}
	
	@RequestMapping("findByName.do")
	@ResponseBody
	public String findByName(String userName, Model modelMap){
		if(userService.findByUsername(userName)==null){
			return Constant.OPERATION_SUCCESS;
		}else{
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping("create.do")
	public String create(Integer id, Model modelMap){
		modelMap.addAttribute("userType", UserType.values());
		
		if(id!= null){
			modelMap.addAttribute("user", userService.findOne(id));
		}
		return "accounts/create";
	}
	
	@RequestMapping("view.do")
	public String view(Integer id, Model modelMap){
		modelMap.addAttribute("userType", UserType.values());
		
		if(id!= null){
			modelMap.addAttribute("user", userService.findOne(id));
		}
		
		return "accounts/view";
	}
	
	@RequestMapping("save.do")
	public String save(User user, Model modelMap, HttpServletRequest request){
		
		try {
			if(StringUtils.isEmpty(request.getParameter("oldPassword"))){
				user.setSalt(Constant.generateSalt(user.getUsername()));
				user.setPassword(Constant.encryptionPassword(user.getPassword(), user.getSalt()));
			}else{
				if(!request.getParameter("oldPassword").equals(user.getPassword())){
					user.setSalt(Constant.generateSalt(user.getUsername()));
					user.setPassword(Constant.encryptionPassword(user.getPassword(), user.getSalt()));
				}
			}
			
			if(user.getId() == null){
				userService.save(user);
			}else{
				userService.update(user);
			}
		
			modelMap.addAttribute(Constant.SUCCESS_MESSAGE, Constant.SUCCESS_ALERT_MESSAGE);
		} catch (Exception e) {
			modelMap.addAttribute(Constant.SUCCESS_MESSAGE, Constant.FAIL_ALERT_MESSAGE);
		}
		
		modelMap.addAttribute("userType", UserType.values());
		modelMap.addAttribute("user", user);
		return "accounts/create";
	}
	
	@RequestMapping(value = "reset.do", produces = "text/html;charset=UTF-8", method=RequestMethod.POST)
	@ResponseBody  
	public String reset(Integer resetId, Model modelMap){
		
		try{

			userService.reset(resetId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping(value = "disable.do", produces = "text/html;charset=UTF-8", method=RequestMethod.POST)
	@ResponseBody  
	public String disable(Integer disableId, Model modelMap){
		
		try{

			userService.disable(disableId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
}
