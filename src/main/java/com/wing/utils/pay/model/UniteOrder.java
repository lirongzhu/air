package com.wing.utils.pay.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by shao on 2016/6/30.
 */
@XmlRootElement(name = "xml")
public class UniteOrder {

    private String appid = "";  //应用ID
    private String mch_id = "";  //商户号
    private String device_info = "WEB";   //终端设备号(门店号或收银设备ID)，默认请传"WEB"，非必填
    private String nonce_str = "";  //随机字符串 不长于32位
    private String sign = "";  //签名
    private String body = "";  //商品描述
    private String detail = "";  //商品详情  非必填
    private String attach = "";  //附加数据，在查询API和支付通知中原样返回  非必填
    private String out_trade_no = "";  //商户系统内部的订单号,32个字符内、可包含字母
    private String fee_type = "CNY";  //符合ISO 4217标准的三位字母代码，默认人民币：CNY  非必填
    private String total_fee = "";  //订单总金额，单位为分
    private String spbill_create_ip = "";  //用户端实际ip
    private String time_start = "";  //交易起始时间  订单生成时间，格式为yyyyMMddHHmmss  非必填
    private String time_expire = "";  //交易结束时间  订单失效时间，格式为yyyyMMddHHmmss  最短失效时间间隔必须大于5分钟  非必填
    private String goods_tag = "";  //商品标记，代金券或立减优惠功能的参数  非必填
    private String notify_url = "";  //通知地址  接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
    private String trade_type = "";  //交易类型  支付类型 (APP)
    private String limit_pay = "";  //指定支付方式  no_credit--指定不能使用信用卡支付  非必填

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }
}
