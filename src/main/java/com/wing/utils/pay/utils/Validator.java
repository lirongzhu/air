package com.wing.utils.pay.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: gsh_wu
 * Date: 11-9-14
 * Time: 上午10:18
 */
public class Validator {
    private static Pattern _ipAddressPattern = Pattern.compile(
            "\\b" +
                    "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
                    "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
                    "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
                    "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])" +
                    "\\b");

    public static boolean isAscii(char c) {
        int i = c;

        if ((i >= 32) && (i <= 126)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean equals(String s1, String s2) {
        if ((s1 == null) && (s2 == null)) {
            return true;
        } else if ((s1 == null) || (s2 == null)) {
            return false;
        } else {
            return s1.equals(s2);
        }
    }

    public static boolean isAddress(String address) {
        if (isNull(address)) {
            return false;
        }

        String[] tokens = address.split(StringPool.AT);

        if (tokens.length != 2) {
            return false;
        }

        for (int i = 0; i < tokens.length; i++) {
            char[] c = tokens[i].toCharArray();

            for (int j = 0; j < c.length; j++) {
                if (Character.isWhitespace(c[j])) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isChar(char c) {
        return Character.isLetter(c);
    }

    public static boolean isChar(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isChar(c[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean isDigit(char c) {
        int x = (int) c;

        if ((x >= 48) && (x <= 57)) {
            return true;
        }

        return false;
    }

    public static boolean isDigit(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean isHex(String s) {
        if (isNull(s)) {
            return false;
        }

        return true;
    }

    public static boolean isHTML(String s) {
        if (isNull(s)) {
            return false;
        }

        if (((s.indexOf("<html>") != -1) || (s.indexOf("<HTML>") != -1)) &&
                ((s.indexOf("</html>") != -1) || (s.indexOf("</HTML>") != -1))) {

            return true;
        }

        return false;
    }

    public static boolean isLUHN(String number) {
        if (number == null) {
            return false;
        }

        number = StringUtils.reverse(number);

        int total = 0;

        for (int i = 0; i < number.length(); i++) {
            int x = 0;

            if (((i + 1) % 2) == 0) {
                x = Integer.parseInt(number.substring(i, i + 1)) * 2;

                if (x >= 10) {
                    String s = Integer.toString(x);

                    x = Integer.parseInt(s.substring(0, 1)) +
                            Integer.parseInt(s.substring(1, 2));
                }
            } else {
                x = Integer.parseInt(number.substring(i, i + 1));
            }

            total = total + x;
        }

        if ((total % 10) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDate(int month, int day, int year) {
        return isGregorianDate(month, day, year);
    }

    public static boolean isGregorianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 1) {
            int febMax = 28;

            if (((year % 4) == 0) && ((year % 100) != 0) ||
                    ((year % 400) == 0)) {

                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        } else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }

    public static boolean isJulianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 1) {
            int febMax = 28;

            if ((year % 4) == 0) {
                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        } else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }

    public static boolean isEmailAddress(String ea) {
        if (isNull(ea)) {
            return false;
        }

        int eaLength = ea.length();

        if (eaLength < 6) {

            // j@j.c

            return false;
        }

        ea = ea.toLowerCase();

        int at = ea.indexOf('@');

        // Unix based email addresses cannot be longer than 24 characters.
        // However, many Windows based email addresses can be longer than 24,
        // so we will set the maximum at 96.

        //int maxEmailLength = 24;
        int maxEmailLength = 96;

        if ((at > maxEmailLength) || (at == -1) || (at == 0) ||
                ((at <= eaLength) && (at > eaLength - 5))) {

            // 123456789012345678901234@joe.com
            // joe.com
            // @joe.com
            // joe@joe
            // joe@jo
            // joe@j

            return false;
        }

        int dot = ea.lastIndexOf('.');

        if ((dot == -1) || (dot < at) || (dot > eaLength - 3)) {

            // joe@joecom
            // joe.@joecom
            // joe@joe.c

            return false;
        }

        if (ea.indexOf("..") != -1) {

            // joe@joe..com

            return false;
        }

        char[] name = ea.substring(0, at).toCharArray();

        for (int i = 0; i < name.length; i++) {
            if ((!isChar(name[i])) &&
                    (!isDigit(name[i])) &&
                    (!isEmailAddressSpecialChar(name[i]))) {

                return false;
            }
        }

        if (isEmailAddressSpecialChar(name[0]) ||
                isEmailAddressSpecialChar(name[name.length - 1])) {

            // .joe.@joe.com
            // -joe-@joe.com
            // _joe_@joe.com

            return false;
        }

        char[] host = ea.substring(at + 1, ea.length()).toCharArray();

        for (int i = 0; i < host.length; i++) {
            if ((!isChar(host[i])) &&
                    (!isDigit(host[i])) &&
                    (!isEmailAddressSpecialChar(host[i]))) {

                return false;
            }
        }

        if (isEmailAddressSpecialChar(host[0]) ||
                isEmailAddressSpecialChar(host[host.length - 1])) {

            // joe@.joe.com.
            // joe@-joe.com-

            return false;
        }

        // postmaster@joe.com

        if (ea.startsWith("postmaster@")) {
            return false;
        }

        // root@.com

        if (ea.startsWith("root@")) {
            return false;
        }

        return true;
    }

    public static boolean isEmailAddressSpecialChar(char c) {

        // LEP-1445

        for (int i = 0; i < _EMAIL_ADDRESS_SPECIAL_CHAR.length; i++) {
            if (c == _EMAIL_ADDRESS_SPECIAL_CHAR[i]) {
                return true;
            }
        }

        return false;
    }

    /**
     * @deprecated Use <code>isEmailAddress</code>.
     */
    public static boolean isValidEmailAddress(String ea) {
        return isEmailAddress(ea);
    }

    public static boolean isName(String name) {
        if (isNull(name)) {
            return false;
        }

        char[] c = name.trim().toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (((!isChar(c[i])) &&
                    (!Character.isWhitespace(c[i]))) ||
                    (c[i] == ',')) {

                return false;
            }
        }

        return true;
    }

    public static boolean isNumber(String number) {
        if (isNull(number)) {
            return false;
        }

        char[] c = number.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNull(Object obj) {
        if (obj instanceof Long) {
            return isNull((Long) obj);
        } else if (obj instanceof String) {
            return isNull((String) obj);
        } else if (obj == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNull(Long l) {
        if ((l == null) || l.longValue() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNull(String s) {
        if (s == null) {
            return true;
        }

        s = s.trim();

        if ((s.equals(StringPool.NULL)) || (s.equals(StringPool.BLANK))) {
            return true;
        }

        return false;
    }

    public static boolean isNull(Object[] array) {
        if ((array == null) || (array.length == 0)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isNotNull(Long l) {
        return !isNull(l);
    }

    public static boolean isNotNull(String s) {
        return !isNull(s);
    }

    public static boolean isNotNull(Object[] array) {
        return !isNull(array);
    }

    public static boolean isPassword(String password) {
        if (isNull(password)) {
            return false;
        }

        if (password.length() < 4) {
            return false;
        }

        char[] c = password.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if ((!isChar(c[i])) &&
                    (!isDigit(c[i]))) {

                return false;
            }
        }

        return true;
    }

    public static String extractDigits(String s) {
        if (s == null) {
            return StringPool.BLANK;
        }

        StringMaker sm = new StringMaker();

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (Validator.isDigit(c[i])) {
                sm.append(c[i]);
            }
        }

        return sm.toString();
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return isNumber(extractDigits(phoneNumber));
    }

    public static boolean isMobileNumber(String mobileNumber){
        if(isNull(mobileNumber))
            return false;
        return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(mobileNumber).matches();
    }

    public static boolean isIdCard(String idCard){
        if(isNull(idCard))
            return false;
        //15位和18位身份证号码的正则表达式
        String regIdCard="^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        //如果通过该验证，说明身份证格式正确，但准确性还需计算
        if(Pattern.compile(regIdCard).matcher(idCard).matches()){
            if(idCard.length()==18){
                int[] idCardWi=new int[]{ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; //将前17位加权因子保存在数组里
                int[] idCardY=new int[]{ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 }; //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                int idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和
                for(int i=0;i<17;i++){
                    idCardWiSum+=Integer.valueOf(idCard.substring(i,i+1))*idCardWi[i];
                }
                int idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置
                String idCardLast=idCard.substring(17);//得到最后一位身份证号码

                //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                if(idCardMod==2){
                    return "x".equals(idCardLast.toLowerCase());
                }else{
                    //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                    return String.valueOf(idCardLast).equals(String.valueOf(idCardY[idCardMod]));
                }
            }
        }
        return false;
    }

    public static boolean isVariableTerm(String s) {
        if (s.startsWith("[$") && s.endsWith("$]")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isIPAddress(String ipAddress) {
        Matcher matcher = _ipAddressPattern.matcher(ipAddress);

        return matcher.matches();
    }

    private static char[] _EMAIL_ADDRESS_SPECIAL_CHAR = new char[]{
            '.', '!', '#', '$', '%', '&', '\'', '*', '+', '-', '/', '=', '?', '^',
            '_', '`', '{', '|', '}', '~'
    };
}
