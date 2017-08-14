
// ~ Package Declaration
// ==================================================

package com.wing.controller.restful;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.wing.common.constant.Constant;
import com.wing.common.enums.PaymentStatus;
import com.wing.common.enums.PaymentType;
import com.wing.service.PaymentService;
import com.wing.utils.pay.utils.DatetimeUtil;
import com.wing.utils.pay.utils.Validator;
import com.wing.utils.util.JSONUtil;
import com.wing.utils.util.PropertyUtil;
import com.wing.bean.Payment;

// ~ Comments
// ==================================================

@RestController
public class AliPayController {

	// ~ Static Fields
	// ==================================================

	// ~ Fields
	// ==================================================

	// ~ Constructors
	// ==================================================

	// ~ Methods
	// ==================================================

  private static final Logger logger = LoggerFactory.getLogger(AliPayController.class);
  
  @Autowired
  private PaymentService paymentService;

  private static final String paycfg = "{\"port\":\"10300\"," +
          "\"app_id\":\"2016013101131853\"," +
          "\"timeout_express\":\"30m\"," +
          "\"format\":\"json\"," +
          "\"charset\":\"utf-8\"," +
          "\"sign_type\":\"RSA2\"," +
          "\"RSA2_PRIVATE\":\"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCXagztGiBLNY+qCdQW8DS1hSIxAXWo1c894HQYkTbOHnQtx8N4e9sriaC6u1vKByo45Z9qVLEbBEg1byShucFU7+sIyBKucPUVFKGVESNeZ2tzITJXTlQNexQscrHcI8iKCipIVvlUnmTyl5sc+WwbV0Oq0CzZrKdFDRj6C66SZewHEW/+Xoe5ZSzbrOMkrihaQVBg9JgXpd23ycYAHxKHBzlQ5YCMR1C/YIe+3WG9WYYVQUF8oRS8HRPifwG/5HQIL6KZvSuHo9ctEtuYoeoMG1QgGxkegFFGJyvACvFgBnwesvGQhFk7XU//IFREmEzIUxns5WrVBkpmxR63pnG7AgMBAAECggEAMediG0NhIWil0QUsQeMpfCpE+qHghaTyiRJRMUerKIQhBnWwlyU7Nz5FG0ZlGlECOX507Da9qIngwru+9w6iCbyJWnuWrKfOHwSNvPPJFHRBldB7JwtJ6KeIHp4uwL+KePSguvbvdlQ+MTaGiSYr21cHlmTGY3fkKX4HnWH8R4OOQtOgQS0yljJ1mnaQXTAZY4aPmj6/V3msZz2J8cjiSmeCfDA/l3dh5REtXD9/9p1DngXhS0+Z/XROKf0TC05VXhpU1+0gU3uxrBOhFJ6JLmrC6AZ2QaZJIJeAJ2yeECV+As0nH/aG1jI453frWZN857QUq8d9DPGPPvd7sMOiAQKBgQDNQK7qFLbT9MX+bKH7NCJrRiI7Wuwn7QRTDkeTyTlnGRzd4arCFgjSmvN3GfpkNxIAYf16mVv1Eeen7k8G4yE+JVjFPNyPs5syJ5OubbqXo4tuk4fg8W4WzhLPO2Q9XEDOOFoZn6hYq7xd2ZnKhpaM8MfY6QVAlDx5MqjislrrcwKBgQC82bRoFX1exhQ7jf9mYM0VNqJ1WIqZnyV6V9mZ8yp093htN0Ge5FYV41i+8blRFCe7nGWKAKbo5ZU8kRFLi5Ww/3oZ0/ECOJWq/12lS4ZB8mBIsUCPsFX88pKlWUOmx22F+rHaD5tykTpd7lVj/PpG6k48dzdKP3o3yXVsd9bemQKBgC3U5bnrSi/mFlgLldfr+kuN65LpaJY5vJ93LtB3PFQjLWj2PRT9qpasdvT/XgQGsARZ3SoFGJ2Ok/XRpt/Y09F2sW3Je06zQlJeuAFZCzNVF4tncg3umzCIb3bKwkVZVjc2KhhSqFq0bmleb5LjnrZ2BYoP7lZPQ3mksEV+ogK1AoGBAKGrHa4c936yw49YOFREeky2wM0ER2LLwyHBIx+P88C+OfIFY7/KaYfDYK3V8XdfccRCVwqVFE48fE2lQtERZcxoaPbDYYctObNxeZGP3heGjrasR0C7DGHGp5O1nxyfjTstKiQrSEAD45uV9LPsVbVv0BojVMnTHaW0aTm6Pc/BAoGBAMQMqZezmy3VQmKvRGu3L8JB0N4FUDgnHZzyZtxPM4HyY/K3QaPXjsycRc7ZVygSNQ3IcNxUwlf1O7rvb+y4yaG0tFwHYg+E4g4NgfUGPsHvLG7KE5uWknX/xqw/TeI0cE+nmunjNbtFCvc5t59Cg8VYQM2yXecZ/x5giIRdTDFr\"," +
          "\"RSA2_PUBLIC\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjrOAHcphTuokF7zlAt1Zk2V1a48MDlN4QKJxpk/kGzLd66hq1VBMuWECQBbeDhq6EA7OuZkrth9pOclv+JYkN8fA9FD8LEgckOWYrhn+GZnIDi1lCH6i//m8uxEUyG7DhSJk76fiXi7S3ljiVvqcBwZPH1KpkZpRTpc+daV4UCPANqKYZqEy1BRMfxGltGSVaTC0aBOIyeEOk4eyuNnhKy1I35f5OBsJ8LKF1314R4CDoFXTtS/C2pvIYZEoXDwnuqLCqZwzr+5NAWuM+jZRFmvqeWXjW5gLnVTQbgM5nqbUnazT2/9fLeNTSKC/qBMxvVsrg4q9pptWf3f/F5QfBQIDAQAB\"," +
          "\"partner\":\"2088911223261409\"," +
          "\"seller_id\":\"jn_lanyi@163.com\"," +
          "\"service\":\"mobile.securitypay.pay\"," +
          "\"input_charset\":\"utf-8\"," +
          "\"appId\":\"2016013101131853\"," +
          "\"RSA_PRIVATE\":\"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALQr6xGtoRzNaD8hGGMMzI7Sb5tmFlkReWIsRdHkwGIkOZndmi3jZ/45qYRnXI+u4ESfh5a6LQUZ9+1XC2mrlSnxBABk/IZui69cEWXejY3gDOk3z/5rXD4isfEDoq8k/hpi8bZ8wWZcYD/b6ORNszB5PXiOY0pHMYoj0G+Vo3VJAgMBAAECgYB4M5C5k5gYSiXRcDf/5QsYAeb5ywr/0zujNpxCIxYCo95V+9Uf5kZ8AmZI8lh7d5Uh0zi3Y3AjpiaVDmyLzDBP0yrvSaOYY3VpV8FCclh2qY0D6poO0Huu4ikES5GqKkSrHNxbKZcr6PD79nEM54erZNKRbxUsSgmqEwcPSazTbQJBAOArQjwYL6HCdA+oLnCyK1BDE6sJW5hXQd1sib2RbgAT355lUkTh7mLfjxU5Ikd6wv7Yc8CqtyZW7ioZoWvSX7sCQQDNwVBqUp5dpZZZaVmZ+alQXNV/3B8hKG+BLLRFGf9i+TjbHemdLkZLPtzkMKYWJIzbtiOM63DvBB7xXS+rPeTLAkAo2PkaxjV3vl0ztSuxMWYfG/nrgqsEGXJRDMQUnPl0XiqzTDcvghPURHRoI58Rc1cw5PRCuVjiTZjcZws5AH6dAkAMniVDT+u8THJK62X+Rkw2zGqfW61hCCzGXCp17mE1/ZiZ/1ybMgJHjQRYzg7xXq5wrtvBQZL8ZWFMZe2Nb88/AkEAjvKOOB3BfTwkPx4hwXY9kbwu/2twEeT5XxQ5BS5K+NAUzYRpiWkqKqlddF36iQNlQHlhrxxaOb9EJDb6iBhMsw==\"," +
          "\"RSA_PUBLIC\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB\"}";
  /**
   * 支付宝统一支付下单接口
   */
  @RequestMapping(value= "/aliPay/unifiedorder/{sn}/{needPayMoney:.+}", method= RequestMethod.GET,produces = "text/html;charset=UTF-8")
  @ResponseBody
  public String unifiedorder(@PathVariable("sn")String sn, @PathVariable("needPayMoney")Double needPayMoney, HttpServletRequest request, HttpServletResponse response){

      JSONObject results = new JSONObject();

      JSONObject cfg = JSONObject.parseObject(paycfg);

      if (sn == null) {
          return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE , "必须传递订单号参数");
      }
      
      Payment payment = paymentService.findBySn(sn);
      		
      if (payment == null) {
          return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE , "该订单不存在");
      }

      Map<String, String> param = createOrderInfo(cfg, sn,needPayMoney);

      param.put("success","true");
      param.put("sn",sn);
      param.put("RSA2_PRIVATE", cfg.getString("RSA2_PRIVATE"));//
      
      results.put("payhtml", param);
      
      return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE , results);
  }

  /**
   * create the order info. 创建订单信息
   */
  private Map<String, String> createOrderInfo(JSONObject cfg, String sn,Double needPayMoney) {

      Map<String, String> param = new HashMap<>();
      
      // 公共请求参数
      param.put("app_id",cfg.getString("app_id"));// 商户订单号
      param.put("method", "alipay.trade.app.pay");// 接口名称	alipay.trade.app.pay
      param.put("format", cfg.getString("format"));
      param.put("charset",  cfg.getString("charset"));//请求使用的编码格式，如utf-8
      param.put("timestamp", DatetimeUtil.formatDateTime(new Date()));
      param.put("version", "1.0");
      param.put("notify_url", PropertyUtil.getServiceAddress() + "resf/alipay/ali_callback"); // 支付宝服务器主动通知商户服务
      param.put("sign_type", cfg.getString("sign_type"));//推荐使用RSA2

      String r = new DecimalFormat("#.00").format(needPayMoney); //
      if (r.startsWith(".")) {
          r = "0" + r;
      } else if (r.startsWith("-.")) {
          r = r.replace("-.", "-0.");
      }
      param.put("out_trade_no", sn); // 商户订单号
      param.put("total_amount", r);// 交易金额r
      param.put("subject", "1"); // 订单标题
      param.put("body", "我是测试数据");// 对交易或商品的描述
      param.put("product_code", "QUICK_MSECURITY_PAY");// 销售产品码
      param.put("timeout_express", cfg.getString("timeout_express"));//

      return param;
  }

  /**
   * ...异步通知服务器支付结果-更新付款状态
   */
  @RequestMapping(value = "/alipay/ali_callback", method = RequestMethod.POST)
  @ResponseBody
  public String execute(HttpServletRequest request, HttpServletResponse response) {

  	// 获取到返回的所有参数 先判断是否交易成功trade_status 再做签名校验
      // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
      // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
      // 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
      // 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
      if (request.getParameter("trade_status") != null && (request.getParameter("trade_status").equals("TRADE_SUCCESS") || request.getParameter("trade_status").equals("TRADE_FINISHED"))) {
          Enumeration<?> pNames = request.getParameterNames();
          Map<String, String> param = new HashMap<String, String>();
          try {
              while (pNames.hasMoreElements()) {
                  String pName = (String) pNames.nextElement();
                  param.put(pName, request.getParameter(pName));
              }
              System.out.println("异步通知响应报文========="+param);
              if (paycfg != null) {

                  JSONObject cfg = JSONObject.parseObject(paycfg);
                  boolean signVerified = AlipaySignature.rsaCheckV1(param, cfg.getString("RSA2_PUBLIC"),
                          AlipayConstants.CHARSET_UTF8,cfg.getString("sign_type")); // 校验签名是否正确

                  if (signVerified) {
                      // TODO 验签成功后
                      // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
                      String sn = param.get("out_trade_no");
                      if (Validator.isNotNull(sn)) { // 修改订单状态
                          System.out.println("22222222222222========="+sn);
                          
                          Payment payment = paymentService.findBySn(sn);
                          if (payment != null) {

                              //避免重复通知
                              if (!(PaymentStatus.已支付 == payment.getPaymentStatus())) {

                                  //修改付款状态
                              	payment.setPaymentStatus(PaymentStatus.已支付);
                              	payment.setPayType(PaymentType.支付宝);
                              	payment.setPaymentDate(new Date());
                              	payment.setActualAmount(Double.valueOf(param.get("total_amount")));
                              	
                              	paymentService.save(payment);

                              }
                              logger.info("订单支付成功：" + JSON.toJSONString(param));

                          } else {
                              logger.error("订单不存在!");
                          }

                      } else {
                          logger.error("返回通知中订单号不存在!");
                          System.out.println("3333333333333========="+sn);
                      }

                  } else {
                      // TODO 验签失败则记录异常日志，并在response中返回failure.
                      System.out.println("22222========校验失败=");
                      logger.debug("验证不通过！");
                  }
              }else{
                  logger.debug("支付方式不存在！");
              }

          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      return "success";
  }
}
