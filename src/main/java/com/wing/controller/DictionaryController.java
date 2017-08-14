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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.common.constant.Constant;
import com.wing.common.enums.DictionaryType;
import com.wing.service.CommonService;
import com.wing.service.DictionaryService;
import com.wing.bean.Dictionary;
import com.wing.bean.DictionaryItem;

@Controller
@RequestMapping("/dictionary/")
public class DictionaryController {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("list.do")
	public String list(@PageableDefault(sort = {"createDate", "dictionaryId"}, direction = Direction.DESC) Pageable pageable, Model modelMap, HttpServletRequest request){
		
		final Specification<Dictionary> spe = new Specification<Dictionary>() {

			@Override
			public Predicate toPredicate(Root<Dictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(StringUtils.isNotBlank(request.getParameter("serach_content"))){
					
					predicates.add(
						cb.or(
							cb.or(
								cb.like(root.get("shortName").as(String.class), "%" + request.getParameter("serach_content") + "%"),
								cb.like(root.get("fullName").as(String.class), "%" + request.getParameter("serach_content") + "%"))
							, 
							cb.or(
								cb.like(root.get("engName").as(String.class), "%" + request.getParameter("serach_content") + "%"),
								cb.like(root.get("engShortName").as(String.class), "%" + request.getParameter("serach_content") + "%"))));
				}
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		modelMap.addAttribute("pagedList", dictionaryService.findAll(spe, pageable));
		return "dictionary/list";
	}
	
	@RequestMapping("create.do")
	public String create(Integer id, Model modelMap, Pageable pageable){
		
		if(id != null){
			modelMap.addAttribute("dictionary", dictionaryService.findOne(id));
		}
		
		modelMap.addAttribute("dictionaryType", DictionaryType.values());
		return "dictionary/create";
	}
	
	@RequestMapping(value = "save.do")
	public String save(Dictionary dictionary, Model modelMap, Pageable pageable){
		
		if(dictionary.getDictionaryId() == null){
			dictionaryService.save(dictionary);
		}else{
			dictionaryService.update(dictionary);
		}
		
		modelMap.addAttribute(Constant.SUCCESS_MESSAGE, Constant.SUCCESS_ALERT_MESSAGE);
		modelMap.addAttribute("dictionary", dictionary);
		modelMap.addAttribute("dictionaryType", DictionaryType.values());
		return "dictionary/create";
	}
	
	@RequestMapping(value = "enable.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String enable(Integer enableId, Model modelMap){
		
		try{
			
			dictionaryService.enable(enableId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping(value = "reset.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String reset(Integer resetId, Model modelMap){
		
		try{
			
			dictionaryService.reset(resetId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping("view.do")
	public String view(Integer id, Model modelMap, Pageable pageable){
		
		Dictionary dictionary = dictionaryService.findOne(id);
		
		modelMap.addAttribute("dictionary", dictionary);
		modelMap.addAttribute("dictionaryItemList", dictionaryService.findItemByDictionary(dictionary, new Sort(Direction.ASC, "itemSort")));
		return "dictionary/view";
	}
	
	@RequestMapping("listItem.do")
	public String listItem(@PageableDefault(sort = {"itemSort"}, direction = Direction.ASC) Pageable pageable, Model modelMap, HttpServletRequest request){
		
		final Specification<DictionaryItem> spe = new Specification<DictionaryItem>() {

			@Override
			public Predicate toPredicate(Root<DictionaryItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(StringUtils.isNotBlank(request.getParameter("serach_content"))){
					
					predicates.add(
						cb.or(
							cb.or(
								cb.like(root.get("shortName").as(String.class), "%" + request.getParameter("serach_content") + "%"),
								cb.like(root.get("fullName").as(String.class), "%" + request.getParameter("serach_content") + "%"))
							, 
							cb.or(
								cb.like(root.get("engName").as(String.class), "%" + request.getParameter("serach_content") + "%"),
								cb.like(root.get("engShortName").as(String.class), "%" + request.getParameter("serach_content") + "%"))));
				}
				
				predicates.add(cb.equal(root.get("dictionary").get("dictionaryId").as(Integer.class), request.getParameter("dictionaryId")));
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		modelMap.addAttribute("pagedList", dictionaryService.findAllItem(spe, pageable));
		modelMap.addAttribute("dictionary", dictionaryService.findOne(Integer.valueOf(request.getParameter("dictionaryId"))));
		return "dictionary/list_item";
	}
	
	@RequestMapping("createItem.do")
	public String createItem(Integer id, Integer itemId, Model modelMap, Pageable pageable){
		
		modelMap.addAttribute("dictionary", dictionaryService.findOne(id));
		
		if(itemId != null){
			modelMap.addAttribute("dictionaryItem", dictionaryService.findItemOne(itemId));
		}
		
		return "dictionary/create_item";
	}
	
	@RequestMapping(value = "saveItem.do")
	public String saveItem(DictionaryItem dictionaryItem, Model modelMap, Pageable pageable){
		
		if(dictionaryItem.getItemId() == null){
			dictionaryService.saveItem(dictionaryItem);
		}else{
			dictionaryService.updateItem(dictionaryItem);
		}
		
		modelMap.addAttribute(Constant.SUCCESS_MESSAGE, Constant.SUCCESS_ALERT_MESSAGE);
		modelMap.addAttribute("dictionary", dictionaryService.findOne(dictionaryItem.getDictionary().getDictionaryId()));
		return "dictionary/create_item";
	}
	
	@RequestMapping(value = "enableItem.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String enableItem(Integer enableItemId, Model modelMap){
		
		try{
			
			dictionaryService.enableItem(enableItemId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping(value = "resetItem.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String resetItem(Integer resetItemId, Model modelMap){
		
		try{
			
			dictionaryService.resetItem(resetItemId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping(value = "checkKey.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String checkKey(String key, String type, Model modelMap){
		
		Integer returnValue = 0;
		
		if("item".equals(type)){
			returnValue = commonService.findItemByItemKey(null, key) == null ? 0 :1;
		}else{
			returnValue = commonService.findDictionaryByKey(null, key) == null ? 0 :1;
		}
		
		return returnValue + "";
	}
}
