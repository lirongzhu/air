package com.wing.controller.restful;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wing.common.constant.Constant;
import com.wing.common.enums.EnableStatus;
import com.wing.common.enums.PaymentStatus;
import com.wing.service.BrowseHistoryService;
import com.wing.service.CommonService;
import com.wing.service.CustomerService;
import com.wing.service.InformationService;
import com.wing.service.PaymentService;
import com.wing.service.TourismService;
import com.wing.utils.cache.impl.CountITourismVisitorCache;
import com.wing.utils.cache.impl.CountRouteVisitorCache;
import com.wing.utils.util.FileUploadUtil;
import com.wing.utils.util.JSONUtil;
import com.wing.utils.util.SendSmsUtil;
import com.wing.bean.BrowseHistory;
import com.wing.bean.Customer;
import com.wing.bean.DictionaryItem;
import com.wing.bean.Information;
import com.wing.bean.Payment;
import com.wing.bean.TourismCostStatement;
import com.wing.bean.TourismFeature;
import com.wing.bean.TourismItinerary;
import com.wing.bean.TourismScheduling;
import com.wing.bean.TourismSpecialNotice;

@RestController
public class RestfulController {

	private static final Logger logger = LogManager.getLogger(RestfulController.class.getName());
	
	@Autowired
	private TourismService tourismService;
	
	@Autowired
	private InformationService informationService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BrowseHistoryService browseHistoryService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 新增用户
	 * @param loginName
	 * @param realName
	 * @param idCard
	 * @param phoneNum
	 * @param sex
	 * @return
	 */
	@RequestMapping(value = "/customer/{loginName}/{realName}/{idCard}/{phoneNum}/{sex}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String saveCustomer(@PathVariable("loginName")String loginName, 
			@PathVariable("realName")String realName,
			@PathVariable("idCard")String idCard,
			@PathVariable("phoneNum")String phoneNum,
			@PathVariable("sex")Integer sex){
		
		try{
			
			if(StringUtils.isBlank(loginName)){
				return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
			}
			
			if(customerService.findByLoginName(loginName) == null){
				
				Customer customer = new Customer();
				customer.setLoginName(loginName);
				customer.setTrueName(realName);
				customer.setContact(phoneNum);
				customer.setSex(sex);
				customer.setIdCard(idCard);
				customer.setCreateDate(new Date());
				customer.setState(EnableStatus.正常);
				
				customerService.save(customer);
			}
			
			countITourismVisitor();
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, "");
		}catch (Exception e) {
			logger.error("save customer error", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 查询缓存
	 * @return
	 */
	@RequestMapping(value = "/cheakCache", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String cheakCache(HttpServletRequest request){
		
		try{
			
			String countITourismVisitorCache = new CountITourismVisitorCache().getAll();
			String countRouteVisitorCache = new CountRouteVisitorCache().getAll();
			
			Map<String, String> map = new HashMap<String, String>(){
				{
					this.put("CountITourismVisitorCache", countITourismVisitorCache);
					this.put("countRouteVisitorCache", countRouteVisitorCache);
				}
			};
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, map);
		}catch (Exception e) {
			logger.error("cheakCache", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 发送验证码
	 * @param phoneNum
	 * @return
	 */
	@RequestMapping(value = "/sendSms/{phoneNumber}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String sendSms(@PathVariable("phoneNumber")String phoneNumber){
		
		String random = Constant.createRandom(true, 6);
		String note = "";
		
		try {
		
			note = "您本次验证码是"+ random +",请在页面中提交验证码完成验证。";
			
			Map<String, String> map = SendSmsUtil.send(phoneNumber, note);	
			
			if(map != null && "0".equalsIgnoreCase(map.get("result"))){
				return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, random);
			}else{
				return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, map);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
	}
	
	/**
	 * 根据ID 查询线路
	 * @param itineraryId
	 * @return
	 */
	@RequestMapping(value = "/tourismItinerary/{itineraryId}/{username}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getTourism(@PathVariable("itineraryId")Integer itineraryId, @PathVariable("username")String username, HttpServletRequest request){
		
		try{
			
			TourismItinerary ti = tourismService.findItineraryById(itineraryId);
			
			TourismFeature feature = tourismService.findFeatureByItinerary(ti);
			
			if(feature != null){
				feature.setTourismItinerary(null);
				ti.setTourismFeature(feature);
			}
			
			List<TourismCostStatement> costList = tourismService.findCostStatementByItinerary(ti);
			
			if(costList != null){
				for(TourismCostStatement cost : costList){
					cost.setTourismItinerary(null);
				}
				
				ti.setCostStatementList(costList);
			}
			
			List<TourismScheduling> schedulingList = tourismService.findSchedulingByItinerary(ti);
			
			if(costList != null){
				for(TourismScheduling sch : schedulingList){
					sch.setTourismItinerary(null);
				}
				
				ti.setSchedulingList(schedulingList);
			}
			
			List<TourismSpecialNotice> specialNoticeList = tourismService.findSpecialNoticeByItinerary(ti);
			
			if(costList != null){
				for(TourismSpecialNotice spe : specialNoticeList){
					spe.setTourismItinerary(null);
				}
				
				ti.setSpecialNoticeList(specialNoticeList);
			}
			
			if(!"undefined".equals(username) && this.getCurrentCustomer(username) != null){
				
				BrowseHistory bh = browseHistoryService.findByCustomerAndTourismItinerary(this.getCurrentCustomer(username), ti);
				
				if(bh == null){
					browseHistoryService.save(new BrowseHistory(this.getCurrentCustomer(username), ti));
				}else{
					bh.setBrowseDate(new Date());
					browseHistoryService.save(bh);
				}
			}
			
			countRouteVisitorCache(itineraryId);
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, SerializerFeature.DisableCircularReferenceDetect, ti);
		}catch (Exception e) {
			logger.error("getTourism", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, new TourismItinerary());
		}
	}
	
	
	/**
	 * 线路列表
	 * @param currentPage
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	@RequestMapping(value = "/tourismItinerary/{currentPage}/{pageSize}/{direction}/{properties}/{functionIndex}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getAllTourism(
			@PathVariable("currentPage")Integer currentPage, 
			@PathVariable("pageSize")Integer pageSize,
			@PathVariable("direction")String direction,
			@PathVariable("properties")String properties,
			@PathVariable("functionIndex") Integer functionIndex){
		
		try{
			PageRequest page = new PageRequest(currentPage, pageSize, Direction.fromString(direction), properties);
			
			final Specification<TourismItinerary> spe = new Specification<TourismItinerary>() {

				@Override
				public Predicate toPredicate(Root<TourismItinerary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					
					List<Predicate> predicates = new ArrayList<>();
					
					if(functionIndex != 0){
						
						DictionaryItem functionItem = null;
						
						switch(functionIndex){
							case 1 : functionItem = commonService.findItemByItemKey(null, "CT"); break;
							case 2 : functionItem = commonService.findItemByItemKey(null, "GTL"); break;
							case 3 : functionItem = commonService.findItemByItemKey(null, "BWS"); break;
							case 4 : functionItem = commonService.findItemByItemKey(null, "PT"); break;
							case 5 : functionItem = commonService.findItemByItemKey(null, "WW"); break;
							case 6 : functionItem = commonService.findItemByItemKey(null, "TF"); break;
							case 7 : functionItem = commonService.findItemByItemKey(null, "INFO"); break;
							case 8 : functionItem = commonService.findItemByItemKey(null, "CD"); break;
							default : functionItem = new DictionaryItem(); break;
						}
						
						predicates.add(cb.equal(root.get("belongFunction").as(DictionaryItem.class), functionItem));
					}

					predicates.add(cb.equal(root.get("enable").as(EnableStatus.class), EnableStatus.正常));
					predicates.add(cb.equal(root.get("hasDelete").as(Integer.class), 0));
					predicates.add(cb.equal(root.get("examineResult").as(DictionaryItem.class), commonService.findItemByItemKey(null, "approved")));
					Predicate[] p = new Predicate[predicates.size()];  
				    return cb.and(predicates.toArray(p));
				}
			};
			
			Page<TourismItinerary> list = tourismService.findAll(page, spe);
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, SerializerFeature.DisableCircularReferenceDetect, list);
		}catch (Exception e) {
			logger.error("getAllTourism", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	@RequestMapping(value = "/tourismItinerary/{currentPage}/{pageSize}/{direction}/{properties}/{keyWord}/{functionIndex}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getAllTourism(
			@PathVariable("currentPage")Integer currentPage, 
			@PathVariable("pageSize")Integer pageSize,
			@PathVariable("direction")String direction,
			@PathVariable("properties")String properties,
			@PathVariable("keyWord")String keyWord,
			@PathVariable("functionIndex") Integer functionIndex){
		
		try{
			PageRequest page = new PageRequest(currentPage, pageSize, Direction.fromString(direction), properties);
			
			final Specification<TourismItinerary> spe = new Specification<TourismItinerary>() {

				@Override
				public Predicate toPredicate(Root<TourismItinerary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					
					List<Predicate> predicates = new ArrayList<>();
					
					if(StringUtils.isNotBlank(keyWord)){
						
						predicates.add(
							cb.or(
									cb.like(root.get("keyWord").as(String.class), "%" + keyWord + "%"), 
									cb.or(
										cb.like(root.get("routeName").as(String.class), "%" + keyWord + "%"),
										cb.like(root.get("destination").as(String.class), "%" + keyWord + "%")
									)
								)
							);
					}
					
					if(functionIndex != 0){
						
						DictionaryItem functionItem = null;
						
						switch(functionIndex){
							case 1 : functionItem = commonService.findItemByItemKey(null, "CT"); break;
							case 2 : functionItem = commonService.findItemByItemKey(null, "GT"); break;
							case 3 : functionItem = commonService.findItemByItemKey(null, "BWS"); break;
							case 4 : functionItem = commonService.findItemByItemKey(null, "PT"); break;
							case 5 : functionItem = commonService.findItemByItemKey(null, "WW"); break;
							case 6 : functionItem = commonService.findItemByItemKey(null, "TF"); break;
							case 7 : functionItem = commonService.findItemByItemKey(null, "INFO"); break;
							case 8 : functionItem = commonService.findItemByItemKey(null, "CD"); break;
							default : functionItem = new DictionaryItem(); break;
						}
						
						predicates.add(cb.equal(root.get("belongFunction").as(DictionaryItem.class), functionItem));
					}
					
					predicates.add(cb.equal(root.get("enable").as(EnableStatus.class), EnableStatus.正常));
					predicates.add(cb.equal(root.get("hasDelete").as(Integer.class), 0));
					predicates.add(cb.equal(root.get("examineResult").as(DictionaryItem.class), commonService.findItemByItemKey(null, "approved")));
					Predicate[] p = new Predicate[predicates.size()];  
				    return cb.and(predicates.toArray(p));
				}
			};
			
			Page<TourismItinerary> list = tourismService.findAll(page, spe);
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, SerializerFeature.DisableCircularReferenceDetect, list);
		}catch (Exception e) {
			logger.error("getAllTourism", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 热门资讯列表
	 * @return
	 */
	@RequestMapping(value = "/information/getHot", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getInformationHot(){
		
		try{
			
			List<Information> list = new ArrayList<Information>();
			
			list.add(informationService.findByHotType(0).size() == 0 ? null : informationService.findByHotType(0).get(0));
			list.add(informationService.findByHotType(1).size() == 0 ? null : informationService.findByHotType(1).get(0));
			list.add(informationService.findByHotType(2).size() == 0 ? null : informationService.findByHotType(2).get(0));
			
			for(Information ifor : list){
				ifor.setInformationContent("");
			}
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, list);
		}catch (Exception e) {
			e.printStackTrace();
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 轮播图
	 * @return
	 */
	@RequestMapping(value = "/information/getCarouselFigure", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String carouselFigure(){
		
		try{
			
			List<Information> list = informationService.findByHotType(3);
			for(Information ifor : list){
				ifor.setInformationContent("");
			}
			
			for(Information ifor : list){
				ifor.setInformationContent("");
			}

			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, list);
		}catch (Exception e) {
			e.printStackTrace();
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 出行资讯
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/information/getTravel/{currentPage}/{pageSize}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getInformationTravel(@PathVariable("currentPage")Integer currentPage, @PathVariable("pageSize")Integer pageSize){
		
		try{
			
			PageRequest page = new PageRequest(currentPage, pageSize, Direction.DESC, "informationId");
			
			final Specification<Information> spe = new Specification<Information>() {

				@Override
				public Predicate toPredicate(Root<Information> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					
					List<Predicate> predicates = new ArrayList<>();
					
					predicates.add(cb.equal(root.get("informationType").as(DictionaryItem.class), commonService.findItemByItemKey(null, "travel")));
					
					Predicate[] p = new Predicate[predicates.size()];  
				    return cb.and(predicates.toArray(p));
				}
			};
			
			Page<Information> pageList = informationService.findAll(spe, page);
			
			for(Information info : pageList.getContent()){
				info.setInformationContent("");
			}
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, pageList);
		}catch (Exception e) {
			logger.error("getInformationTravel", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 根据ID获取信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/information/{id}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getInformation(@PathVariable("id")Integer id){
		
		try{
			
			Information info = informationService.findOne(id);
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, info);
		}catch (Exception e) {
			logger.error("getTourismDiscount", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, new Information());
		}
	}
	
	/**
	 * 浏览记录
	 * @return
	 */
	@RequestMapping(value = "/browseHistory/{username}/{currentPage}/{pageSize}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getBrowseHistory(@PathVariable("currentPage")Integer currentPage, @PathVariable("username")String username, @PathVariable("pageSize")Integer pageSize, HttpServletRequest request){
		
		try{
			
			Pageable page = new PageRequest(currentPage, pageSize, Direction.DESC, "browseDate");
			
			if(this.getCurrentCustomer(username) == null){
				return JSONUtil.returnMessage(Constant.NO_LOGIN_USER, Constant.NO_LOGIN_USER_MESSAGE, "");
			}
			
			Page<BrowseHistory> pageList = browseHistoryService.findByCustomer(page , this.getCurrentCustomer(username));
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, SerializerFeature.DisableCircularReferenceDetect, pageList);
		}catch (Exception e) {
			logger.error("getBrowseHistory" , e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 订单记录
	 * @return
	 */
	@RequestMapping(value = "/payment/{username}/{currentPage}/{pageSize}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getPayment(@PathVariable("username")String username, @PathVariable("currentPage")Integer currentPage, @PathVariable("pageSize")Integer pageSize, HttpServletRequest request){
		
		try{
			
			if(this.getCurrentCustomer(username) == null){
				return JSONUtil.returnMessage(Constant.NO_LOGIN_USER, Constant.NO_LOGIN_USER_MESSAGE, "");
			}
			
			Pageable page = new PageRequest(currentPage, pageSize, Direction.DESC, "createDate");
			
			final Specification<Payment> spe = new Specification<Payment>() {

				@Override
				public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					
					List<Predicate> predicates = new ArrayList<>();
					
					predicates.add(cb.equal(root.get("createCustomer").get("id").as(Integer.class), getCurrentCustomer(username).getId()));
					
					Predicate[] p = new Predicate[predicates.size()];  
				    return cb.and(predicates.toArray(p));
				}
			};
			
			Page<Payment> pageList = paymentService.findAll(spe, page);
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, SerializerFeature.DisableCircularReferenceDetect,  pageList);
		}catch (Exception e) {
			logger.error("getPayment", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 订单查询
	 * @return
	 */
	@RequestMapping(value = "/payment/{id}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String payment(@PathVariable("id")Integer id, HttpServletRequest request){
		
		try{
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, SerializerFeature.DisableCircularReferenceDetect,  paymentService.findOne(id));
		}catch (Exception e) {
			logger.error("payment", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 订单查询
	 * @return
	 */
	@RequestMapping(value = "/paymentBySn/{sn}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getPaymentBySn(@PathVariable("sn")String sn, HttpServletRequest request){
		
		try{
			
			Payment payment = paymentService.findBySn(sn);
			
			Map<String, String> returnValue = new HashMap<>();
			
			if(payment == null){
				returnValue.put("success", "订单不存在");
				return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, returnValue);
			}
			if(payment.getPaymentStatus() == PaymentStatus.已支付){
				returnValue.put("success", "true");
			}else{
				returnValue.put("success", "false");
			}
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, returnValue);
		}catch (Exception e) {
			logger.error("payment", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 新增订单
	 * @param loginName
	 * @param realName
	 * @param idCard
	 * @param phoneNum
	 * @param sex
	 * @return
	 */
	@RequestMapping(value = "/payment/{itemCount}/{childItemCount}/{tourismDeparture}/{adultUnitPrice}/{childUnitPrice}/{totalPrice}/{actualAmount}/{contactsName}/{phoneNumber}/{QQNumber}/{weixinNumber}/{travelers1}/{travelers2}/{remark}/{deposit}/{username}", 
			method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	
	public String savePayment(@PathVariable("itemCount")Integer itemCount, 
	        @PathVariable("childItemCount")Integer childItemCount,
			@PathVariable("tourismDeparture")Integer tourismDepartureId,
			@PathVariable("adultUnitPrice")Double adultUnitPrice,
			@PathVariable("childUnitPrice")Double childUnitPrice,
			@PathVariable("totalPrice")Double totalPrice,
			@PathVariable("actualAmount")Double actualAmount,
			@PathVariable("contactsName")String contactsName,
			@PathVariable("phoneNumber")String phoneNumber,
			@PathVariable("QQNumber")String QQNumber,
			@PathVariable("weixinNumber")String weixinNumber,
			@PathVariable("travelers1")String travelers1,
			@PathVariable("travelers2")String travelers2,
			@PathVariable("remark")String remark,
			@PathVariable("deposit")Integer deposit,
			@PathVariable("username")String username,
			HttpServletRequest request){
		
		try{
			
			if(this.getCurrentCustomer(username) == null){
				return JSONUtil.returnMessage(Constant.NO_LOGIN_USER, Constant.NO_LOGIN_USER_MESSAGE, "");
			}
			
			String sn = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String uuid = UUID.randomUUID().toString().replace("-", "");
			sn = sn + uuid.substring(0, 8);
       
			Payment payment = new Payment();
			payment.setActualAmount(actualAmount);
			payment.setContactsName(contactsName);
			payment.setCreateCustomer(this.getCurrentCustomer(username));
			payment.setCreateDate(new Date());
			payment.setItemCount(itemCount);
			payment.setChildItemCount(childItemCount);
			payment.setPhoneNumber(phoneNumber);
			payment.setQQNumber(QQNumber);
			payment.setRemark(remark);
			payment.setTotalPrice(totalPrice);
			payment.setTravelers1(travelers1);
			payment.setTravelers2(travelers2);
			payment.setAdultUnitPrice(adultUnitPrice);
			payment.setChildUnitPrice(childUnitPrice);
			payment.setWeixinNumber(weixinNumber);
			payment.setPaymentStatus(PaymentStatus.未支付);
			payment.setSn(sn);
			payment.setPaymentDate(new Date());
			payment.setDeposit(deposit);
			
			paymentService.save(payment);
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, payment);
		}catch (Exception e) {
			logger.error("savePayment", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 新增订单
	 * @param loginName
	 * @param realName
	 * @param idCard
	 * @param phoneNum
	 * @param sex
	 * @return
	 */
	@RequestMapping(value = "/paymentFail/{sn}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String paymentFail(@PathVariable("sn")String sn ,HttpServletRequest request){
		
		try{
			
			Payment payment = paymentService.findBySn(sn);
			payment.setPaymentStatus(PaymentStatus.支付失败);
			payment.setPaymentDate(new Date());
			
			paymentService.save(payment);
			
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, payment);
		}catch (Exception e) {
			logger.error("savePayment", e);
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	/**
	 * 提交评价
	 * @param evaluateMessage
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/evaluate/{evaluateMessage}", method = RequestMethod.POST , produces = "multipart/form-data;charset=UTF-8")
    public String evaluate(@PathVariable("evaluateMessage") String evaluateMessage, MultipartRequest request) {

        String returnMessage = "";

        try {

        	FileUploadUtil.uploadImage(request);
            returnMessage = JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE , "上传成功");
        } catch (IOException e) {
        	logger.error("evaluate" , e);
            returnMessage = JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE , "上传失败");
        }

        return returnMessage;
    }
	
	/**
	 * 获取当前用户
	 * @param request
	 * @return
	 */
	/*private Customer getCurrentCustomer(HttpServletRequest request){
		
		String loginName = "";
		
		if(StringUtils.isNoneBlank(request.getHeader("currentLoginName"))){
			loginName =  request.getHeader("currentLoginName");
		}
		
		return loginName == "" ? null : customerService.findByLoginName(loginName);
	}*/
	
	/**
	 * 获取当前用户
	 * @param request
	 * @return
	 */
	private Customer getCurrentCustomer(String loginName){
		
		return StringUtils.isBlank(loginName) ? null : customerService.findByLoginName(loginName);
	}
	
	/**
	 * <pre>
	 * 2017年7月11日 lirongzhu
	 * 	加入计算线路访问者缓存
	 * </pre>
	 * 
	 * @param itineraryId
	 */
	private synchronized void countRouteVisitorCache(Integer itineraryId){
		
		CountRouteVisitorCache crv = new CountRouteVisitorCache();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String key = sdf.format(new Date());
		if(crv.get(key) != null){
			
			Map<Integer, Integer> map = (Map<Integer, Integer>)crv.get(key);
			
			if(map.get(itineraryId) == null){
				map.put(itineraryId, 1);
			}else{
				map.put(itineraryId, map.get(itineraryId) + 1);
			}
			
			crv.put(key, map);
		}else{
			crv.put(key, new HashMap<Integer, Integer>(){{this.put(itineraryId, 1);}});
		}
	}
	
	/**
	 * <pre>
	 * 2017年7月11日 lirongzhu
	 * 	加入I旅游访问者缓存
	 * </pre>
	 * 
	 */
	private synchronized void countITourismVisitor(){
		
		CountITourismVisitorCache citv = new CountITourismVisitorCache();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String key = sdf.format(new Date());
		if(citv.get(key) != null){
			citv.put(key, Integer.valueOf(citv.get(key).toString()) + 1);
		}else{
			citv.put(key, 1);
		}
	}
}
