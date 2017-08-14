package com.wing.utils.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtil {

    public static String returnMessage(String code, String message, Object data){

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("code", code);
        returnMap.put("message", message);
        returnMap.put("data", data);

        return JSON.toJSONString(returnMap);
    }
    
    public static String returnMessage(String code, String message, SerializerFeature serializerFeature, Object data){

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("code", code);
        returnMap.put("message", message);
        returnMap.put("data", data);

        return JSON.toJSONString(returnMap,serializerFeature);
    }
}
