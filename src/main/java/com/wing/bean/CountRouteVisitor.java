package com.wing.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author
 * 爱旅游每日访问人数
 */
@Entity
@Table(name = "crm_count_route_visitor")
public class CountRouteVisitor {
	
	@Id
	@TableGenerator(name = "tg_count_route_visitor", table = "tg_id_table", pkColumnName = "table_name", 
		pkColumnValue = "crm_count_route_visitor", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_count_route_visitor")
	private Integer id;
	
	@Column(name = "count_num")
	private Integer countNum;
	
	@Column(name = "itinerary_id")
	private Integer itineraryId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "visit_date", nullable = false, length = 19)
	@JSONField(format="yyyy-MM-dd HH:mm:dd")
	private Date visitDate;

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

	
	/**
	 * @return 传回 countNum。
	 */
	public Integer getCountNum() {
	
		return countNum;
	}

	
	/**
	 * @param countNum 要设定的 countNum。
	 */
	public void setCountNum(Integer countNum) {
	
		this.countNum = countNum;
	}

	
	/**
	 * @return 传回 visitDate。
	 */
	public Date getVisitDate() {
	
		return visitDate;
	}

	
	/**
	 * @param visitDate 要设定的 visitDate。
	 */
	public void setVisitDate(Date visitDate) {
	
		this.visitDate = visitDate;
	}

	
	/**
	 * @return 传回 itineraryId。
	 */
	public Integer getItineraryId() {
	
		return itineraryId;
	}

	
	/**
	 * @param itineraryId 要设定的 itineraryId。
	 */
	public void setItineraryId(Integer itineraryId) {
	
		this.itineraryId = itineraryId;
	}
}
