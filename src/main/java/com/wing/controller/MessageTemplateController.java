package com.wing.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.common.constant.Constant;
import com.wing.common.enums.UserType;
import com.wing.service.MessageReceiverService;
import com.wing.service.MessageTemplateService;
import com.wing.service.UserService;
import com.wing.utils.util.PropertyUtil;
import com.wing.bean.MessageReceiver;
import com.wing.bean.MessageTemplate;
import com.wing.bean.User;

@Controller
@RequestMapping("/message/")
public class MessageTemplateController {

	private static final Logger logger = LogManager.getLogger(MessageTemplateController.class.getName());
	
	@Autowired
	private MessageTemplateService templateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageReceiverService receiverService;
	
	@RequestMapping("list.do")
	public String list(Model modelMap, Pageable pageable,HttpServletRequest request){
		
		final Specification<MessageTemplate> spe = new Specification<MessageTemplate>() {

			@Override
			public Predicate toPredicate(Root<MessageTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(StringUtils.isNotBlank(request.getParameter("serach_content"))){
					
					predicates.add(
						cb.or(
							cb.like(root.get("templateName").as(String.class), "%" + request.getParameter("serach_content") + "%")));
				}
				
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		modelMap.addAttribute("pagedList", templateService.findAll(spe,pageable));
		return "message/list";
	}
	
	@RequestMapping("create.do")
	public String create(Integer id, Model modelMap, Pageable pageable){
		if(id!=null){
			MessageTemplate messageTemplate = templateService.findOne(id);
			modelMap.addAttribute("messageTemplate", messageTemplate);
			
			modelMap.addAttribute("serviceAddress", PropertyUtil.getServiceAddress());
		}	
		
		return "message/create";
	}
	
	@RequestMapping("save.do")
	public String save(MessageTemplate messageTemplate,Model modelMap){
		
		if(messageTemplate.getTemplateId()!=null){
			templateService.update(messageTemplate);
		}else{
			templateService.save(messageTemplate);
		}
		
		return "redirect:update.do?id="+messageTemplate.getTemplateId();
	}
	
	@RequestMapping("update.do")
	public String update(Integer id, Model modelMap, Pageable pageable){
		if(id!=null){
			MessageTemplate messageTemplate = templateService.findOne(id);
			modelMap.addAttribute("messageTemplate", messageTemplate);
			
			modelMap.addAttribute("serviceAddress", PropertyUtil.getServiceAddress());
		}	
		modelMap.addAttribute(Constant.SUCCESS_MESSAGE, Constant.SUCCESS_ALERT_MESSAGE);
		return "message/create";
	}
	
	@ResponseBody
	@RequestMapping("delete.do")
	public String delete(Integer id){
		
		try {
			templateService.delete(id);
			return Constant.OPERATION_SUCCESS;
		}
		catch (Exception e) {
			logger.error(e);
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping("createReceiver.do")
	public String createReceiver(Integer templateId,Model modelMap,Pageable pageable){
		
		MessageTemplate messageTemplate = templateService.findOne(templateId);
		modelMap.addAttribute("messageTemplate",messageTemplate);
		modelMap.addAttribute("user",userService.findAll());
		modelMap.addAttribute("pagedList",receiverService.findByMessageTemplate(pageable,messageTemplate));
		modelMap.addAttribute("userType", UserType.values());
		return "message/create_receiver";
	}
	
	@ResponseBody
	@RequestMapping("saveReceiver.do")
	public String saveReceiver(Integer templateId,String userId){

		String [] arr = userId.split(";");

		try {
			List<MessageReceiver> receiverList = new ArrayList<MessageReceiver>();
			MessageTemplate messageTemplate = templateService.findOne(templateId);

			for(String ar : arr){
				
				MessageReceiver messageReceiver = new MessageReceiver();
				User user = userService.findOne(Integer.valueOf(ar));
				
				if(receiverService.findByUserAndMessageTemplate(user, messageTemplate) == null){
					
					messageReceiver.setMessageTemplate(messageTemplate);
					messageReceiver.setUser(user);
					receiverList.add(messageReceiver);
				}
			}
			
			receiverService.save(receiverList);
			return Constant.OPERATION_SUCCESS;
		}
		catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@ResponseBody
	@RequestMapping("saveSole.do")
	public String saveSole(Integer templateId,String userType){

		try {
			
			MessageTemplate messageTemplate = templateService.findOne(templateId);
			
			List<MessageReceiver> receiverList = new ArrayList<MessageReceiver>();
			List<User> list = userService.findByUserType(UserType.valueOf(userType));
			
			for(User user : list){
				
				if(receiverService.findByUserAndMessageTemplate(user, messageTemplate)==null){
					
					MessageReceiver messageReceiver = new MessageReceiver();
					messageReceiver.setMessageTemplate(messageTemplate);
					messageReceiver.setUser(user);
					receiverList.add(messageReceiver);
				}
			}
			
			receiverService.save(receiverList);
			return Constant.OPERATION_SUCCESS;
		}
		catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@ResponseBody
	@RequestMapping("deleteReceiver.do")
	public String deleteReceiver(Integer receiverId){
		try {
			receiverService.delete(receiverId);
			return Constant.OPERATION_SUCCESS;
		}
		catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
}
