package com.wing.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.wing.common.constant.Constant;
import com.wing.service.CommonService;
import com.wing.service.InformationService;
import com.wing.utils.util.FileUploadUtil;
import com.wing.utils.util.PropertyUtil;
import com.wing.bean.Information;

@Controller
@RequestMapping("/information/")
public class InformationController {
	
	@Autowired
	private InformationService informationService;
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("list.do")
	public String list(Model modelMap, Pageable pageable,HttpServletRequest request){
		
		final Specification<Information> spe = new Specification<Information>() {

			@Override
			public Predicate toPredicate(Root<Information> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(StringUtils.isNotBlank(request.getParameter("serach_content"))){
					
					predicates.add(
						cb.or(
							cb.like(root.get("informationTitle").as(String.class), "%" + request.getParameter("serach_content") + "%")));
				}
				
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		modelMap.addAttribute("pagedList", informationService.findAll(spe,pageable));
		
		return "information/list";
	}
	
	@RequestMapping("create.do")
	public String create(Integer id, Model modelMap, Pageable pageable){
		
		if(id!= null){
			
			Information information = informationService.findOne(id);
			modelMap.addAttribute("information", information);
		}
		
		modelMap.addAttribute("informationType",commonService.findItemByDictionaryKey(null, "IT"));
		modelMap.addAttribute("serviceAddress", PropertyUtil.getServiceAddress());
		
		return "information/create";
	}
	
	@RequestMapping(value="save.do" , produces = "multipart/form-data;charset=UTF-8")
	public String save(Information information ,MultipartRequest request, Model modelMap){
		
		File file = null;
		
		try {
			file = FileUploadUtil.uploadImage(request);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(file != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			information.setPicPath(Constant.SHOW_IMAGE_URL + sdf.format(new Date()) + System.getProperty("file.separator") + file.getName());
			information.setPicThumbnailPath(Constant.SHOW_THUMBNAIL_IMAGE_URL + sdf.format(new Date()) + System.getProperty("file.separator") + file.getName());
		}
		
		if(information.getInformationId() == null){
			informationService.save(information);
		}else{
			informationService.update(information);
		}
		
		return "redirect:list.do";
	}
	
	@RequestMapping("view.do")
	public String view(Integer id, Model modelMap, Pageable pageable){
		
		if(id!= null){
			
			Information information = informationService.findOne(id);
			modelMap.addAttribute("information", information);
		}
		
		return "information/view";
	}
	
	@RequestMapping("delete.do")
	@ResponseBody
	public String delete(Integer deleteId){
		
		try {
			
			informationService.delete(deleteId);
			
			return Constant.OPERATION_SUCCESS;
		} catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping("setHot.do")
	@ResponseBody
	public String setHot(Integer infoId, Integer hotType){
		
		try {
			
			informationService.setHot(infoId, hotType);
			
			return Constant.OPERATION_SUCCESS;
		} catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping("cancelHot.do")
	@ResponseBody
	public String cancelHot(Integer cancelId){
		
		try {
			
			informationService.cancelHot(cancelId);
			
			return Constant.OPERATION_SUCCESS;
		} catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
}
