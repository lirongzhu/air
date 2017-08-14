package com.wing.utils.pay.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by shao on 2016/7/3.
 */
public class WechatPayUtil {
    private String callbackUrl;

    public static String getSign(Object object, String key) {
        String temp = getParamStr(object);
        if (temp != null && temp.length() > 2) {
            temp = temp + "&key=" + key;
        }
        return MD5Util.md5For32(temp).toUpperCase();
    }

    public static String getParamStr(Object object) {
        if (object == null)
            return null;
        JSONObject obj = (JSONObject) JSONObject.toJSON(object, new ParserConfig());
        LinkedHashMap<String, String> jsonMap = JSONObject.parseObject(obj.toJSONString(), new TypeReference<LinkedHashMap<String, String>>() {
        });
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().equals("")) {
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        if (sb.length() < 2)
            return null;
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String getNonceStr() {
        String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        str = str + uuid.substring(0, 18);
        return str;
    }

}
