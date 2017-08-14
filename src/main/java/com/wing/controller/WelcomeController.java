package com.wing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wing.common.constant.Constant;
import com.wing.common.enums.OperationType;
import com.wing.common.enums.UserType;
import com.wing.service.CommonService;
import com.wing.service.OperationLogService;
import com.wing.service.TourismService;
import com.wing.service.UserService;
import com.wing.utils.util.PropertyUtil;
import com.wing.bean.DictionaryItem;
import com.wing.bean.OperationLog;
import com.wing.bean.TourismItinerary;
import com.wing.bean.User;

@Controller
@RequestMapping("/welcome/")
public class WelcomeController {

	@Autowired
	private OperationLogService operationLogService;
	
	@Autowired
	private TourismService tourismService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value= "list.do", method=RequestMethod.GET)
	public String welcome(ModelMap model){
		
		User currentUser = Constant.getCurrentUser();
		
		if(currentUser.getUserType() == UserType.路线审核员){
			
			final Specification<TourismItinerary> spe = new Specification<TourismItinerary>() {

				@Override
				public Predicate toPredicate(Root<TourismItinerary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					
					List<Predicate> predicates = new ArrayList<>();
					
					predicates.add(cb.equal(root.get("examineResult").as(DictionaryItem.class), commonService.findItemByItemKey(null, "submitted")));
					
					Predicate[] p = new Predicate[predicates.size()];  
				    return cb.and(predicates.toArray(p));
				}
			};
			
			model.addAttribute("waitForExamine", tourismService.findAll(new PageRequest(0, 3, Direction.DESC, "itineraryId"), spe));
		}
		
		final Specification<TourismItinerary> spe = new Specification<TourismItinerary>() {

			@Override
			public Predicate toPredicate(Root<TourismItinerary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(cb.equal(root.get("createUser").get("id").as(Integer.class), Constant.getCurrentUser().getId()));
				predicates.add(cb.equal(root.get("examineResult").as(DictionaryItem.class), commonService.findItemByItemKey(null, "submitted")));
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		model.addAttribute("myTourism", tourismService.findAll(new PageRequest(0, 3, Direction.DESC, "itineraryId"), spe));
		
		List<OperationLog> operationLogList = operationLogService.findByLoginUser(new PageRequest(0, 1 , new Sort(Direction.DESC,"id")), currentUser).getContent();
		model.addAttribute("myLoginInfo",operationLogList.size() > 0 ? operationLogList.get(0) : new OperationLog());
		
		OperationLog operationLog = new OperationLog();
		operationLog.setOperationDate(new Date());
		operationLog.setOperationUser(currentUser);
		operationLog.setOperationContent(currentUser.getContactName() + "登录系统");
		operationLog.setOperationType(OperationType.登录);
		
        operationLogService.save(operationLog);
		
    	return "welcome/welcome";
	}
	
	@RequestMapping("updateInfo.do")
	public String create(Model modelMap){
		modelMap.addAttribute("userType", UserType.values());
		modelMap.addAttribute("user", Constant.getCurrentUser());
		
		return "welcome/change_info";
	}
	
	@RequestMapping("save.do")
	public String save(User user, Model modelMap, HttpServletRequest request){
		
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
		
		try {
			modelMap.addAttribute(Constant.SUCCESS_MESSAGE, Constant.SUCCESS_ALERT_MESSAGE);
		} catch (Exception e) {
			modelMap.addAttribute(Constant.SUCCESS_MESSAGE, Constant.FAIL_ALERT_MESSAGE);
		}
		
		modelMap.addAttribute("userType", UserType.values());
		modelMap.addAttribute("user", user);
		return "welcome/change_info";
	}
}
