package com.wing.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.wing.common.enums.PaymentStatus;
import com.wing.common.enums.PaymentType;

/**
 * @author 杨康
 * 订单支付
 */
@Entity
@Table(name = "crm_payment")
public class Payment {
	
	@Id
    @TableGenerator(name = "tg_payment", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_payment", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_payment")
	private Integer paymentId;
	
	//商户订单号
	@Column(name = "payment_sn", unique = true,nullable = false,length = 128)
	private String sn;
  
	/**
	 * 成人数量 人数/房间数
	 */
	@Column(name = "item_count", length = 2)
	private Integer itemCount;
	
	/**
	 * 成人数量 人数/房间数
	 */
	@Column(name = "child_item_count", length = 2)
	private Integer childItemCount;
	
	/**
	 * 代金券
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cash_coupon_id", referencedColumnName = "id")
    private CashCoupon cashCoupon;
	
	//成人单价
	@Column(name = "adult_unit_price", precision = 14, scale = 3)
	private Double adultUnitPrice;
	
	//儿童单价
	@Column(name = "child_unit_price", precision = 14, scale = 3)
	private Double childUnitPrice;
	
	//总价
	@Column(name = "total_price", precision = 14, scale = 3)
	private Double totalPrice;
	
	//实际支付
	@Column(name = "actual_amount", precision = 14, scale = 3)
	private Double actualAmount;
	
	/**
	 * 0线下付款、1预付定金、2全额支付
	 */
	@Column(name = "deposit", length = 1)
	private Integer deposit;
	
	@Column(name = "contacts_name",  length = 64)
	private String contactsName;
	
	@Column(name = "phone_number",  length = 32)
	private String phoneNumber;
	
	@Column(name = "qq_number",  length = 20)
	private String QQNumber;
	
	@Column(name = "weixin_number",  length = 64)
	private String weixinNumber;
	
	@Column(name = "travelers1",  length = 256)
	private String travelers1;
	
	@Column(name = "travelers2",  length = 256)
	private String travelers2;
	
	@Column(name = "route_name",  length = 256)
	private String remark;
	
	/**
     * 创建日期
     */
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date", nullable = false, length = 19)
	@JSONField(format="yyyy-MM-dd HH:mm:ss", serialize=false)
    private Date createDate;
	
	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "payment_date", nullable = false, length = 19)
	@JSONField(format="yyyy-MM-dd HH:mm:ss", serialize=false)
	private Date paymentDate;
	
	@OneToOne(cascade = CascadeType.DETACH) 
	@JoinColumn(name = "create_customer", referencedColumnName = "id")
	private Customer createCustomer;
	
	@Column(name = "payment_status",  length = 1)
	private PaymentStatus paymentStatus;
	
	 //付款方式
  @Column(name = "pay_type", nullable = true, length = 20)
  private PaymentType payType;

	/**
	 * @return the paymentId
	 */
	public Integer getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the itemCount
	 */
	public Integer getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @return the cashCoupon
	 */
	public CashCoupon getCashCoupon() {
		return cashCoupon;
	}

	/**
	 * @param cashCoupon the cashCoupon to set
	 */
	public void setCashCoupon(CashCoupon cashCoupon) {
		this.cashCoupon = cashCoupon;
	}

	/**
	 * @return the totalPrice
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the actualAmount
	 */
	public Double getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount the actualAmount to set
	 */
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createCustomer
	 */
	public Customer getCreateCustomer() {
		return createCustomer;
	}

	/**
	 * @param createCustomer the createCustomer to set
	 */
	public void setCreateCustomer(Customer createCustomer) {
		this.createCustomer = createCustomer;
	}

	/**
	 * @return the contactsName
	 */
	public String getContactsName() {
		return contactsName;
	}

	/**
	 * @param contactsName the contactsName to set
	 */
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the qQNumber
	 */
	public String getQQNumber() {
		return QQNumber;
	}

	/**
	 * @param qQNumber the qQNumber to set
	 */
	public void setQQNumber(String qQNumber) {
		QQNumber = qQNumber;
	}

	/**
	 * @return the weixinNumber
	 */
	public String getWeixinNumber() {
		return weixinNumber;
	}

	/**
	 * @param weixinNumber the weixinNumber to set
	 */
	public void setWeixinNumber(String weixinNumber) {
		this.weixinNumber = weixinNumber;
	}

	/**
	 * @return the travelers1
	 */
	public String getTravelers1() {
		return travelers1;
	}

	/**
	 * @param travelers1 the travelers1 to set
	 */
	public void setTravelers1(String travelers1) {
		this.travelers1 = travelers1;
	}

	/**
	 * @return the travelers2
	 */
	public String getTravelers2() {
		return travelers2;
	}

	/**
	 * @param travelers2 the travelers2 to set
	 */
	public void setTravelers2(String travelers2) {
		this.travelers2 = travelers2;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the adultUnitPrice
	 */
	public Double getAdultUnitPrice() {
		return adultUnitPrice;
	}

	/**
	 * @param adultUnitPrice the adultUnitPrice to set
	 */
	public void setAdultUnitPrice(Double adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	/**
	 * @return the childUnitPrice
	 */
	public Double getChildUnitPrice() {
		return childUnitPrice;
	}

	/**
	 * @param childUnitPrice the childUnitPrice to set
	 */
	public void setChildUnitPrice(Double childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	/**
	 * @return the paymentStatus
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	
	/**
	 * @return 传回 sn。
	 */
	public String getSn() {
	
		return sn;
	}

	
	/**
	 * @param sn 要设定的 sn。
	 */
	public void setSn(String sn) {
	
		this.sn = sn;
	}

	
	/**
	 * @return 传回 paymentDate。
	 */
	public Date getPaymentDate() {
	
		return paymentDate;
	}

	
	/**
	 * @param paymentDate 要设定的 paymentDate。
	 */
	public void setPaymentDate(Date paymentDate) {
	
		this.paymentDate = paymentDate;
	}

	
	/**
	 * @return 传回 payType。
	 */
	public PaymentType getPayType() {
	
		return payType;
	}

	
	/**
	 * @param payType 要设定的 payType。
	 */
	public void setPayType(PaymentType payType) {
	
		this.payType = payType;
	}

	
	/**
	 * @return 传回 childItemCount。
	 */
	public Integer getChildItemCount() {
	
		return childItemCount;
	}

	
	/**
	 * @param childItemCount 要设定的 childItemCount。
	 */
	public void setChildItemCount(Integer childItemCount) {
	
		this.childItemCount = childItemCount;
	}

	
	/**
	 * @return 传回 deposit。
	 */
	public Integer getDeposit() {
	
		return deposit;
	}

	
	/**
	 * @param deposit 要设定的 deposit。
	 */
	public void setDeposit(Integer deposit) {
	
		this.deposit = deposit;
	}
	
}
