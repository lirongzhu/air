package com.wing.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.JSONObject;
import com.wing.common.constant.Constant;
import com.wing.common.enums.DictionaryType;
import com.wing.common.enums.EnableStatus;
import com.wing.service.CommonService;
import com.wing.utils.util.FileUploadUtil;
import com.wing.utils.util.JSONUtil;
import com.wing.bean.Dictionary;
import com.wing.bean.DictionaryItem;

@Controller
@RequestMapping("/common/")
public class CommonController {
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value = "/uploadImage.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
    public String ueUpload(MultipartRequest request) {

		Map<String, Object> returnVal = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
    	try {
			File file = FileUploadUtil.uploadImage(request);
			returnVal.put("state", "SUCCESS");  
			returnVal.put("url", Constant.SHOW_IMAGE_URL + sdf.format(new Date()) + System.getProperty("file.separator") + new String(file.getName().getBytes(), "UTF-8"));  
			returnVal.put("size",request.getFile(request.getFileNames().next()).getSize());  
            returnVal.put("original", new String(file.getName().getBytes(), "UTF-8"));  
            returnVal.put("type", ((HttpServletRequest)request).getContentType()); 
            
		} catch (IllegalStateException e) {
			e.printStackTrace();
			returnVal.put("state", "ERROR");
		} catch (IOException e) {
			e.printStackTrace();
			returnVal.put("state", "ERROR");
		}
    	
        return JSONObject.toJSONString(returnVal);
    }
	
	@RequestMapping(value = "getSystemDictionary.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String getSystemDictionary(Model modelMap){
		try{
			
			List<Dictionary> list = commonService.findAllDictionary(EnableStatus.正常, DictionaryType.系统字典);
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, list);
		}catch (Exception e) {
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	@RequestMapping(value = "getBusinessDictionary.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String getBusinessDictionary(Model modelMap){
		try{
			
			List<Dictionary> list = commonService.findAllDictionary(EnableStatus.正常, DictionaryType.业务字典);
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, list);
		}catch (Exception e) {
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
	
	@RequestMapping(value = "getDictionaryItemByParent.do", produces = "text/html;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody  
	public String getDictionaryItemByParent(String dictionaryKey, Model modelMap){
		try{
			
			List<DictionaryItem> list = commonService.findItemByDictionaryKey(EnableStatus.正常, dictionaryKey);
			return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE, list);
		}catch (Exception e) {
			return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE, "");
		}
	}
}
