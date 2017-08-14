package com.wing.bean;

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

/**
 * @author 杨康
 *	费用说明
 */
@Entity
@Table(name = "crm_tourism_cost_statement")
public class TourismCostStatement {
	
	@Id
    @TableGenerator(name = "tg_tourism_cost_statement", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_tourism_cost_statement", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_tourism_cost_statement")
    private Integer costStatementId;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "itineraryId", updatable=false)
    private TourismItinerary tourismItinerary;
	
	/**
	 * 费用说明
	 */
	@Column(name = "cost_statement",  length = 256)
	private String costStatement;
	
	/**
	 * @return the costStatement
	 */
	public String getCostStatement() {
		return costStatement;
	}

	/**
	 * @param costStatement the costStatement to set
	 */
	public void setCostStatement(String costStatement) {
		this.costStatement = costStatement;
	}

	/**
	 * @return the costStatementId
	 */
	public Integer getCostStatementId() {
		return costStatementId;
	}

	/**
	 * @param costStatementId the costStatementId to set
	 */
	public void setCostStatementId(Integer costStatementId) {
		this.costStatementId = costStatementId;
	}

	/**
	 * @return the tourismItinerary
	 */
	public TourismItinerary getTourismItinerary() {
		return tourismItinerary;
	}

	/**
	 * @param tourismItinerary the tourismItinerary to set
	 */
	public void setTourismItinerary(TourismItinerary tourismItinerary) {
		this.tourismItinerary = tourismItinerary;
	}
	
	
}
