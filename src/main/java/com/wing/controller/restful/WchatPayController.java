package com.wing.controller.restful;

import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.wing.common.constant.Constant;
import com.wing.common.enums.PaymentStatus;
import com.wing.common.enums.PaymentType;
import com.wing.service.PaymentService;
import com.wing.utils.pay.model.UniteOrder;
import com.wing.utils.pay.model.UniteOrderBack;
import com.wing.utils.pay.utils.HttpsUtils;
import com.wing.utils.pay.utils.IPUtils;
import com.wing.utils.pay.utils.JaxbUtil;
import com.wing.utils.pay.utils.WechatPayUtil;
import com.wing.utils.pay.utils.XmlUtil;
import com.wing.utils.util.JSONUtil;
import com.wing.utils.util.PropertyUtil;
import com.wing.bean.Payment;

/**
 * Created by mianmian on 2017/5/22.
 */
@RestController
public class WchatPayController {

    private static final Logger log = LoggerFactory.getLogger(WchatPayController.class);
    private static final String paycfg = "{\"callback_encoding\":\"UTF-8\",\"callback_encodingName\":\"UTF-8\",\"key\":\"jnlanwingiyfm1264fhwrepwq1985072\",\"return_encoding\":\"UTF-8\",\"return_encodingName\":\"UTF-8\",\"postUrl\":\"https://api.mch.weixin.qq.com/pay/unifiedorder\",appId:\"wxacf484b241f72853\",machId:\"1285844501\",type:\"APP\",port:10300,\"queryUrl\":\"https://api.mch.weixin.qq.com/pay/orderquery\"}";

    @Autowired
    private PaymentService paymentService;
    /**
     * 微信统一支付下单接口
     */
    @RequestMapping(value= "/wchatPay/unifiedorder/{sn}/{needPayMoney:.+}", method= RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String unifiedorder(@PathVariable("sn")String sn, @PathVariable("needPayMoney")Double needPayMoney, HttpServletRequest request, HttpServletResponse response){
        
    	String ip = IPUtils.getIpAddr(request);  //能否获取到客户端ip
      log.info("客户端IP：" + ip);

        JSONObject results = new JSONObject();
        JSONObject cfg = JSONObject.parseObject(paycfg);
        String  payhtml="";
        
        UniteOrder order = new UniteOrder();
        order.setAppid(cfg.getString("appId"));
        order.setMch_id(cfg.getString("machId"));
        order.setNonce_str(WechatPayUtil.getNonceStr());
        order.setBody("支付");
        //order.setOut_trade_no(payenable.getSn());
        order.setOut_trade_no(""+sn);
        //order.setTotal_fee(String.valueOf((int) (payenable.getNeedPayMoney() * 100)));
        order.setTotal_fee(String.valueOf((int)(needPayMoney* 100)));
        order.setSpbill_create_ip(ip); // 获取设备IP
        order.setNotify_url(PropertyUtil.getServiceAddress() + "resf/wchatPay/wechat_callback"); // 异步通知接口
        order.setTrade_type(cfg.getString("type"));

        String sign = WechatPayUtil.getSign(order, cfg.getString("key"));
        order.setSign(sign);

        JaxbUtil util = new JaxbUtil("UTF-8", false, UniteOrder.class);
        String xml = util.toXml(order);

        log.info("发起支付-统一下单：" + xml);
        String result = HttpsUtils.postStringToUrl(xml, cfg.getString("postUrl"), "GET");
        if (result == null) {
        	return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE , "接口调用失败");
        } else {
            JaxbUtil u = new JaxbUtil("UTF-8", false, UniteOrderBack.class);
            UniteOrderBack back = u.fromXml(result);

            if (back.getReturn_code().trim().equals("SUCCESS") && back.getResult_code().trim().equals("SUCCESS")) {
            	
                //再次签名
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("appid", back.getAppid());
                map.put("partnerid", back.getMch_id());
                map.put("prepayid", back.getPrepay_id());
                map.put("noncestr", back.getNonce_str());
                map.put("timestamp", new Date().getTime() / 1000);
                map.put("package", "Sign=WXPay");
                String s = WechatPayUtil.getSign(map, cfg.getString("key"));
                map.put("sign", s);
                map.put("sn", sn);
                
                results.put("payhtml", map);
            } else {
            	 return JSONUtil.returnMessage(Constant.OPERATION_FAIL, Constant.FAIL_ALERT_MESSAGE , "接口调用失败");
            }
        }
       
        log.info("预订单结果：");
        log.info(payhtml);
        
        return JSONUtil.returnMessage(Constant.OPERATION_SUCCESS, Constant.SUCCESS_ALERT_MESSAGE , results);
    }

    /**
     * ...异步通知服务器支付结果-更新付款状态
     */
    @RequestMapping("/wchatPay/wechat_callback")
    @ResponseBody
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("微信异步通知更新开始=========");
        try {
        	System.out.println("1234567=========");
            JSONObject object = XmlUtil.parseXml(request);
            System.out.println("111111111========="+object.toString());
            if (object != null) {
                if (object.getString("return_code").equals("SUCCESS")) {
                    if (object.getString("result_code").equals("SUCCESS")) {
                        log.info("支付成功！");
                        log.info(object.toJSONString());

                        //支付成功后的操作
                        paySucc(object.getString("out_trade_no"), object.getIntValue("total_fee"));

                    } else {
                        log.info("支付失败！");
                        log.info("信息：" + object.toJSONString());
                    }
                    return "<xml>\n" +
                            "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                            "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                            "</xml>";
                } else {
                    System.out.println("33333333333333333=========");
                    log.error("接收微信APP支付返回通知失败！");
                    log.error("信息：" + object.toJSONString());
                    return "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";

    }

    private void paySucc(String sn, int totalFee) {// TODO  修改订单状态
        if (sn == null || sn.trim().equals("")) {
            log.error("支付成功回调失败！");
            return;
        }
        System.out.println("2222222222222========="+sn);
        
        Payment payment = paymentService.findBySn(sn);
        //避免重复通知
        if (payment != null && !(PaymentStatus.已支付 == payment.getPaymentStatus())) {
            
        	//修改付款状态
            payment.setPaymentStatus(PaymentStatus.已支付);
          	payment.setPayType(PaymentType.微信);
          	payment.setPaymentDate(new Date());
          	payment.setActualAmount(totalFee / 100.0);
          	
          	paymentService.save(payment);
        }

    }
}
