package com.wing.utils.pay.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyStore;

/**
 * Created by shao on 2016-01-22.
 */
public class HttpsUtils {

    public static Logger log = LoggerFactory.getLogger(HttpsUtils.class);

    /**
     * post普通表单
     *
     * @param url
     * @param params
     * @return
     */
    public static String httpsPost(String url, JSONObject params) {
        return httpsPost(url, params, null);
    }

    /**
     * @param url
     * @param params
     * @param charSet 服务器端字符集
     * @return
     */
    public static String httpsPost(String url, JSONObject params, String charSet) {
        return httpsPost(url, params, charSet, null, null);
    }

    /**
     * @param url
     * @param params
     * @param jksStorePath 证书库地址
     * @param jksPwd       证书库密码
     * @return
     */
    public static String httpsPost(String url, JSONObject params, String jksStorePath, String jksPwd) {
        return httpsPost(url, params, null, jksStorePath, jksPwd);
    }

    /**
     * @param url
     * @param params
     * @param charSet      服务器端字符集
     * @param jksStorePath 证书库地址
     * @param jksPwd       证书库密码
     * @return
     */
    public static String httpsPost(String url, JSONObject params, String charSet, String jksStorePath, String jksPwd) {
        JSONObject result = new JSONObject();
        if (url == null || (!url.startsWith("https://") && !url.startsWith("HTTPS://"))) {
            log.error("url is not correct !");
            result.put("success", false);
            result.put("msg", "url is not correct !");
            return result.toJSONString();
        }
        if (params == null || params.size() == 0) {
            log.error("params can not be empty !");
            result.put("success", false);
            result.put("msg", "params can not be empty !");
            return result.toJSONString();
        }
        StringBuilder paramStr = new StringBuilder();
        for (String k : params.keySet()) {
            paramStr.append(k).append("=").append(params.get(k)).append("&");
        }
        paramStr.deleteCharAt(paramStr.length() - 1);

        try {
            URL postUrl = new URL(url);
            HttpsURLConnection postConn = (HttpsURLConnection) postUrl.openConnection();

            //设置信任证书
            if (jksStorePath != null) {
                postConn.setSSLSocketFactory(getSSLContext(jksPwd, jksStorePath, jksStorePath).getSocketFactory());
            }

            // 设置请求头信息
            postConn.setRequestProperty("accept", "*/*");
            postConn.setRequestProperty("Connection", "Keep-Alive");
            postConn.setRequestProperty("Charset", "UTF-8");
            postConn.setRequestProperty("Content-Length", String.valueOf(paramStr.length()));
            //默认是GET
            postConn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            postConn.setDoOutput(true);
            postConn.setDoInput(true);
            // post方式不能使用缓存
            postConn.setUseCaches(false);

            //设置连接主机超时（单位：毫秒）
            postConn.setConnectTimeout(30000);
            //设置从主机读取数据超时（单位：毫秒）
            postConn.setReadTimeout(60000);
            postConn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });


            /// 获得输出流
            PrintWriter printWriter = new PrintWriter(postConn.getOutputStream());
            printWriter.write(paramStr.toString());
            printWriter.flush();
            printWriter.close();

            // 根据ResponseCode判断连接是否成功
            int responseCode = postConn.getResponseCode();
            if (responseCode != 200) {
                log.error("connect '" + url + "' error,responseCode is " + responseCode + " !");
                result.put("success", false);
                result.put("msg", "connect '" + url + "' error,responseCode is " + responseCode + " !");
                return result.toJSONString();
            }
            StringBuffer resp = new StringBuffer();
            BufferedReader reader = null;
            // 定义BufferedReader输入流来读取URL的响应
            if (Validator.isNull(charSet))
                reader = new BufferedReader(new InputStreamReader(
                        postConn.getInputStream()));
            else
                reader = new BufferedReader(new InputStreamReader(
                        postConn.getInputStream(), charSet));
            String line = null;
            while ((line = reader.readLine()) != null) {
                resp.append(line);
            }
            postConn.disconnect();
            return resp.toString();
        } catch (Exception e) {
            log.error("post 请求出现异常：{}", e.toString());
            result.put("msg", e.toString());
            result.put("success", false);
            result.put("errCode", -1);
            return result.toJSONString();
        }
    }

    /**
     * 获得KeyStore.
     *
     * @param keyStorePath 密钥库路径
     * @param password     密码
     * @return 密钥库
     * @throws Exception
     */
    private static KeyStore getKeyStore(String password, String keyStorePath)
            throws Exception {
        // 实例化密钥库
        KeyStore ks = KeyStore.getInstance("JKS");
        // 获得密钥库文件流
        FileInputStream is = new FileInputStream(keyStorePath);
        // 加载密钥库
        ks.load(is, password.toCharArray());
        // 关闭密钥库文件流
        is.close();
        return ks;
    }

    /**
     * 获得SSLSocketFactory.
     *
     * @param password       密码
     * @param keyStorePath   密钥库路径
     * @param trustStorePath 信任库路径
     * @return SSLSocketFactory
     * @throws Exception
     */
    private static SSLContext getSSLContext(String password,
                                            String keyStorePath, String trustStorePath) throws Exception {
        // 实例化密钥库
        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        // 获得密钥库
        KeyStore keyStore = getKeyStore(password, keyStorePath);
        // 初始化密钥工厂
        keyManagerFactory.init(keyStore, password.toCharArray());

        // 实例化信任库
        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // 获得信任库
        KeyStore trustStore = getKeyStore(password, trustStorePath);
        // 初始化信任库
        trustManagerFactory.init(trustStore);
        // 实例化SSL上下文
        SSLContext ctx = SSLContext.getInstance("TLS");
        // 初始化SSL上下文
        ctx.init(keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(), null);
        // 获得SSLSocketFactory
        return ctx;
    }

    /**
     * @param str
     * @param url
     * @return
     */
    public static String postStringToUrl(String str, String url,String requestMethod) {
       return postStringToUrl(str,url,requestMethod,null);
    }

    public static String postStringToUrl(String str, String url,String requestMethod,String charSet){
        StringBuffer buffer = new StringBuffer();
        try {

            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            //打开连接
            URL u = new URL(url);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) u.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)){
                httpUrlConn.connect();
            }
            // 当有数据需要提交时
            if (null != str) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(str.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            String cs=Validator.isNull(charSet)?"UTF-8":charSet;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, cs);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                buffer.append(s);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            log.error("connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return null;
    }

}
