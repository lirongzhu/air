package com.wing.common.constant;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

import com.wing.bean.User;
import com.wing.utils.util.PropertyUtil;

public class Constant {

	public static final String NO_LOGIN_USER = "99";
	public static final String OPERATION_SUCCESS = "1";
	public static final String OPERATION_FAIL = "0";
	
	public static final String WARN_MESSAGE = "warnMessage";
	public static final String INFO_MESSAGE = "infoMessage";
	public static final String SUCCESS_MESSAGE = "successMessage";
	public static final String DANGER_MESSAGE = "dangerMessage";
	
	public static final String SUCCESS_ALERT_MESSAGE = "操作成功！";
	public static final String FAIL_ALERT_MESSAGE = "操作失败！";
	public static final String NO_LOGIN_USER_MESSAGE = "当前无登录用户，请进行登录！";
	
	public static final String IMAGE_UPLOAD_FOLDER = "file_upload" +System.getProperty("file.separator")+ "image";
	public static final String THUMBNAIL_IMAGE_UPLOAD_FOLDER = "file_upload" + System.getProperty("file.separator") + "thumbnail_image";
	
	public static final String SHOW_IMAGE_URL = PropertyUtil.getServiceAddress() + IMAGE_UPLOAD_FOLDER + System.getProperty("file.separator");
	public static final String SHOW_THUMBNAIL_IMAGE_URL = PropertyUtil.getServiceAddress() + THUMBNAIL_IMAGE_UPLOAD_FOLDER + System.getProperty("file.separator");
	
	public static User getCurrentUser(){
		return (User)SecurityUtils.getSubject().getPrincipal();
	}
	
	public static String generateSalt(String unencrypted){
		return new Md5Hash(unencrypted).toString();
	}
	
	public static String encryptionPassword(String unencrypted, String salt){
		
		return new Md5Hash(unencrypted, salt, 5).toString();
	}
	
	/**
	  * 创建指定数量的随机字符串
	  * @param numberFlag 是否是数字
	  * @param length
	  * @return
	  */
	public static String createRandom(boolean numberFlag, int length){
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		}while (bDone);
			return retStr;
	 }
	
	/**
	 * 图片URL转换
	 * @param url
	 * @return
	 */
	public static String transformationImageUrl(String url){
		if(StringUtils.isBlank(url)){
			return PropertyUtil.getServiceAddress() + "images/LONDON-SERPENTINE1.jpg";
		}
		
		return PropertyUtil.getServiceAddress() + IMAGE_UPLOAD_FOLDER + url.replaceAll("\\\\", "/").split(IMAGE_UPLOAD_FOLDER)[1];
	}
	
	/**
	 * 图片URL转换
	 * @param url
	 * @return
	 */
	public static String transformationTextUrl(String text){
		if(StringUtils.isBlank(text)){
			return "";
		}
		
		String replaceText = "";
		
		if(System.getProperty("os.name").toLowerCase().startsWith("win")){
			replaceText = text.replace("..\\.\\", "").replaceAll("\\\\", "/");
		}else{
			replaceText = text.replace(".././", "").replaceAll("\\\\", "/");
		}
		
		String returnText = "";
		
		//绝对路径不在进行replace
		/*if(replaceText.indexOf(PropertyUtil.getServiceAddress() + IMAGE_UPLOAD_FOLDER) == -1){
			returnText = replaceText.replaceAll(IMAGE_UPLOAD_FOLDER, PropertyUtil.getServiceAddress() + IMAGE_UPLOAD_FOLDER);
		}else{
			returnText = replaceText;
		}*/
		
		returnText = replaceText.replaceAll("\"" + IMAGE_UPLOAD_FOLDER, "\"" + PropertyUtil.getServiceAddress() + IMAGE_UPLOAD_FOLDER);
		
		return returnText;
	}
}
