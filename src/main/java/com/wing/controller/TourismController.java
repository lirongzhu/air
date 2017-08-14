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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.wing.common.constant.Constant;
import com.wing.common.enums.EnableStatus;
import com.wing.common.enums.UserType;
import com.wing.service.CommonService;
import com.wing.service.TourismService;
import com.wing.utils.util.FileUploadUtil;
import com.wing.utils.util.PropertyUtil;
import com.wing.bean.TourismItinerary;

@Controller
@RequestMapping("/tourism/")
public class TourismController {
	
	@Autowired
	private TourismService tourismService;
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("list.do")
	public String list(@PageableDefault(sort = {"createDate", "itineraryId"}, direction = Direction.DESC) Pageable pageable, Model modelMap, HttpServletRequest request){
		
		final Specification<TourismItinerary> spe = new Specification<TourismItinerary>() {

			@Override
			public Predicate toPredicate(Root<TourismItinerary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();
				
				if(StringUtils.isNotBlank(request.getParameter("serach_content"))){
					
					predicates.add(
						cb.or(
							cb.like(root.get("productNumber").as(String.class), "%" + request.getParameter("serach_content") + "%"), 
							cb.or(
								cb.like(root.get("routeIntroduction").as(String.class), "%" + request.getParameter("serach_content") + "%"),
								cb.like(root.get("routeName").as(String.class), "%" + request.getParameter("serach_content") + "%"))));
				}
				
				if(Constant.getCurrentUser().getUserType() != UserType.系统管理员){
					predicates.add(cb.equal(root.get("createUser").get("id").as(Integer.class), Constant.getCurrentUser().getId()));
				}
				
				predicates.add(cb.equal(root.get("enable").as(EnableStatus.class), EnableStatus.正常));
				predicates.add(cb.equal(root.get("hasDelete").as(Integer.class), 0));
				
				Predicate[] p = new Predicate[predicates.size()];  
			    return cb.and(predicates.toArray(p));
			}
		};
		
		modelMap.addAttribute("pagedList", tourismService.findAll(pageable, spe));
		return "tourism/list";
	}
	
	@RequestMapping("create.do")
	public String create(Integer id, Model modelMap, Pageable pageable){
		
		if(id!= null){
			
			TourismItinerary ti = tourismService.findItineraryById(id);
			modelMap.addAttribute("itinerary", ti);
			modelMap.addAttribute("feature", tourismService.findFeatureByItinerary(ti));
			modelMap.addAttribute("costStatementList", tourismService.findCostStatementByItinerary(ti));
			modelMap.addAttribute("schedulingList", tourismService.findSchedulingByItinerary(ti));
			modelMap.addAttribute("specialNoticeList", tourismService.findSpecialNoticeByItinerary(ti));
		}
		
		modelMap.addAttribute("routeDays", new ArrayList<Integer>(){{for(int i= 1; i <= 15; i++) this.add(i);}});
		modelMap.addAttribute("tourismType", commonService.findItemByDictionaryKey(null, "PT"));
		modelMap.addAttribute("rangType", commonService.findItemByDictionaryKey(null, "TT"));
		modelMap.addAttribute("cardType", commonService.findItemByDictionaryKey(null, "RD"));
		modelMap.addAttribute("belongFunction", commonService.findItemByDictionaryKey(null, "BF"));
		
		modelMap.addAttribute("serviceAddress", PropertyUtil.getServiceAddress());
		
		return "tourism/create";
	}
	
	@RequestMapping(value = "save.do", produces = "multipart/form-data;charset=UTF-8")
	public String save(TourismItinerary tourismItinerary, MultipartRequest request, Model modelMap, Pageable pageable){
		
		if(tourismItinerary.getItineraryId() == null){
			tourismService.saveAllTourismInfo(tourismItinerary);
		}else{
			tourismService.updateAllTourismInfo(tourismItinerary);
		}
		
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
			
			tourismItinerary.setPictureRelativePath(Constant.SHOW_IMAGE_URL + sdf.format(new Date()) + System.getProperty("file.separator") + file.getName());
			tourismItinerary.setPictureThumbnailPath(Constant.SHOW_THUMBNAIL_IMAGE_URL + sdf.format(new Date()) + System.getProperty("file.separator") + file.getName());
		}
		
		tourismService.update(tourismItinerary);
		
		return "redirect:update.do?id="+tourismItinerary.getItineraryId();
	}
	
	@RequestMapping("update.do")
	public String update(Integer id, Model modelMap, Pageable pageable){
			
		TourismItinerary ti = tourismService.findItineraryById(id);
		modelMap.addAttribute("itinerary", ti);
		modelMap.addAttribute("feature", tourismService.findFeatureByItinerary(ti));
		modelMap.addAttribute("costStatementList", tourismService.findCostStatementByItinerary(ti));
		modelMap.addAttribute("schedulingList", tourismService.findSchedulingByItinerary(ti));
		modelMap.addAttribute("specialNoticeList", tourismService.findSpecialNoticeByItinerary(ti));
		modelMap.addAttribute(Constant.SUCCESS_MESSAGE, Constant.SUCCESS_ALERT_MESSAGE);
		
		modelMap.addAttribute("routeDays", new ArrayList<Integer>(){{for(int i= 1; i <= 15; i++) this.add(i);}});
		modelMap.addAttribute("tourismType", commonService.findItemByDictionaryKey(null, "PT"));
		modelMap.addAttribute("rangType", commonService.findItemByDictionaryKey(null, "TT"));
		modelMap.addAttribute("cardType", commonService.findItemByDictionaryKey(null, "RD"));
		modelMap.addAttribute("belongFunction", commonService.findItemByDictionaryKey(null, "BF"));
		
		modelMap.addAttribute("serviceAddress", PropertyUtil.getServiceAddress());
		
		return "tourism/create";
	}
	
	@RequestMapping(value = "enable.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String enable(Integer enableId, Model modelMap){
		
		try{
			
			tourismService.enable(enableId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping(value = "delete.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String delete(Integer deleteId, Model modelMap){
		
		try{
			
			tourismService.delete(deleteId);
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}

	@RequestMapping(value = "deleteItem.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String deleteItem(Integer deleteId, String deleteType, Model modelMap){
		
		try{
			switch(deleteType){
				case "scheduling" : tourismService.deleteScheduling(deleteId); break;
				case "costStatement" : tourismService.deleteCostStatement(deleteId); break;
				case "specialNotice" : tourismService.deleteSpecialNotice(deleteId); break;
				default : break;
			}
			
			return Constant.OPERATION_SUCCESS;
		}catch (Exception e) {
			return Constant.OPERATION_FAIL;
		}
	}
	
	@RequestMapping("view.do")
	public String view(Integer id, Model modelMap, Pageable pageable){
		
		if(id!= null){
			
			TourismItinerary ti = tourismService.findItineraryById(id);
			modelMap.addAttribute("itinerary", ti);
			modelMap.addAttribute("feature", tourismService.findFeatureByItinerary(ti));
			modelMap.addAttribute("costStatementList", tourismService.findCostStatementByItinerary(ti));
			modelMap.addAttribute("schedulingList", tourismService.findSchedulingByItinerary(ti));
			modelMap.addAttribute("specialNoticeList", tourismService.findSpecialNoticeByItinerary(ti));
		}
		
		return "tourism/view";
	}
}
