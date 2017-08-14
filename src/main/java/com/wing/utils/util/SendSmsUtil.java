package com.wing.utils.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


/**
 * Created by zhangyuan on 2017/2/8.
 */
public class SendSmsUtil {

    public static Map<String, String> send(String mobile, String smsContent) {

        Map<String, String> map = null;
        try{
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod("http://sms.api.ums86.com:8899/sms/Api/Send.do");
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
            post.addParameter("SpCode", "242196");
            post.addParameter("LoginName", "jn_ly");
            post.addParameter("Password", "jnly@1264.");
            post.addParameter("MessageContent",smsContent);
            post.addParameter("UserNumber", mobile);
            post.addParameter("SerialNumber", "");
            post.addParameter("ScheduleTime", "");
            post.addParameter("ExtendAccessNum", "");
            post.addParameter("f", "1");
            httpclient.executeMethod(post);
            String info = new String(post.getResponseBody(),"gbk");

            map = changeStr(info);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    // 解析发送短信返回的信息结果
    public static Map<String, String> changeStr(String str) {

        Map<String, String> map = new HashMap<String, String>();

        String[] strArr = str.split("&");

        for(int i = 0; i < strArr.length; i++) {

            String[] data = strArr[i].split("=");

            map.put(data[0].trim(), data.length == 2 ? data[1].trim() : "");
        }

        return map;
    }
}
