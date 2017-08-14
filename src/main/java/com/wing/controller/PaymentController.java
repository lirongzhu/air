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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wing.common.constant.Constant;
import com.wing.common.enums.UserType;
import com.wing.service.PaymentService;
import com.wing.bean.Payment;
import com.wing.bean.User;

@Controller
@RequestMapping("/payment/")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping("list.do")
	public String list(@PageableDefault(sort = {"paymentDate"}, direction = Direction.DESC)Pageable pageable, 
	                   Model modelMap, HttpServletRequest request){
		
		final Specification<Payment> spe = new Specification<Payment>() {

			@Override
			public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(StringUtils.isNotBlank(request.getParameter("serach_content"))){
					
					predicates.add(
						cb.or(
							cb.or(
								cb.like(root.get("tourismDeparture").get("tourismItinerary").get("routeName").as(String.class), "%" + request.getParameter("serach_content") + "%"),
								cb.like(root.get("tourismDeparture").get("tourismItinerary").get("productNumber").as(String.class), "%" + request.getParameter("serach_content") + "%")
							),
							cb.or(
								cb.like(root.get("contactsName").as(String.class), "%" + request.getParameter("serach_content") + "%"), 
								cb.like(root.get("phoneNumber").as(String.class), "%" + request.getParameter("serach_content") + "%"))
							)
						);
				}
				
				if(Constant.getCurrentUser().getUserType() != UserType.系统管理员){
					predicates.add(
						cb.equal(
							root.get("tourismDeparture").get("tourismItinerary").get("createUser").get("id").as(Integer.class), 
							Constant.getCurrentUser().getId()
						)
					);
				}
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		modelMap.addAttribute("pagedList", paymentService.findAll(spe, pageable));
		
		return "payment/list";
	}
	
	@RequestMapping("view.do")
	public String view(Integer id,Model model){
		
		if(null!=id){
			Payment payment = paymentService.findOne(id);
			model.addAttribute("payment",payment);
			model.addAttribute("customer",payment.getCreateCustomer());
			
		}
		return "payment/view";
	}
}
