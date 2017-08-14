package com.wing.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author 杨康
 *	代金券
 */
@Entity
@Table(name = "crm_cash_coupon")
public class CashCoupon {
	
	@Id
	@TableGenerator(name = "tg_cash_coupon", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_cash_coupon", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cash_coupon")
	private Integer id;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}
