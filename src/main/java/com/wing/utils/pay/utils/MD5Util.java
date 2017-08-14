package com.wing.utils.pay.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: 吴光寿
 * Date: 2010-12-23
 * Time: 16:30:47
 */
public class MD5Util {

    public static String md5For32(String str){
        return str2Md5(str);
    }

    public static String md5For16(String str){
        return str2Md5(str).substring(8, 24);
    }

    private static String str2Md5(String sourceStr){
        String result=null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

}
