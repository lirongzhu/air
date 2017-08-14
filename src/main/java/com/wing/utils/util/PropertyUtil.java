package com.wing.utils.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * @author 
 */
public class PropertyUtil {

    public static String getServiceAddress() {
        Properties prop = new Properties();
        InputStream inputStream = PropertyUtil.class.getResourceAsStream("/serviceAddress.properties");
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String param = prop.getProperty("serviceAddress").trim();
        return param;
    }
    
}