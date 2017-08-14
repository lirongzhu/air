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
import com.wing.service.CustomerService;
import com.wing.bean.Customer;

@Controller
@RequestMapping("/customer/")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("list.do")
	public String list(Pageable pageable, Model modelMap, HttpServletRequest request){
		
		final Specification<Customer> spe = new Specification<Customer>() {

			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(StringUtils.isNotBlank(request.getParameter("serach_content"))){
					
					predicates.add(
						cb.or(
							cb.like(root.get("loginName").as(String.class), "%" + request.getParameter("serach_content") + "%"), 
							cb.or(
								cb.like(root.get("trueName").as(String.class), "%" + request.getParameter("serach_content") + "%"),
								cb.like(root.get("contact").as(String.class), "%" + request.getParameter("serach_content") + "%"))));
				}
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		modelMap.addAttribute("pagedList", customerService.findAll(spe, pageable));
		
		return "customer/list";
	}
	
	@RequestMapping(value = "view.do")
	public String view(Integer id, Model modelMap){
		if(id!= null){
			modelMap.addAttribute("customer", customerService.findOne(id));
		}
		
		return "customer/view";
	}
	
	@RequestMapping(value = "disable.do", produces = "text/html;charset=UTF-8", method=RequestMethod.POST)
	@ResponseBody  
	public String disable(Integer deleteId, Model modelMap){
		try{
			
			customerService.disable(deleteId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping(value = "reset.do", produces = "text/html;charset=UTF-8", method=RequestMethod.POST)
	@ResponseBody  
	public String reset(Integer resetId, Model modelMap){
		try{

			customerService.reset(resetId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
}
