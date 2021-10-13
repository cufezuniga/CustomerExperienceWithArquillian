package demo.arquillian.monolith.customer.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "account", schema = "monolith")
public class Account implements Serializable {

	private static final long serialVersionUID = -1880366110190448509L;
	
	public Account() {
		
	}
	
	@Id
	private String id;

	@Column(name = "startDate", columnDefinition = "DATE")
	private LocalDate startDate;
	
	@Column(name = "lastPurchase", columnDefinition = "DATE")
	private LocalDate lastPurchase;
	
	private int points;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setStartDate(LocalDate sd) {
		this.startDate = sd;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setLastPurchase(LocalDate lp) {
		this.lastPurchase = lp;
	}
	
	public LocalDate getLastPurchase() {
		return lastPurchase;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
	
	@Override
	public String toString() {
		return String.format("Account [id: %s, startDate: %s, lastPurchase: %s, points: %d", getId(), getStartDate(), getLastPurchase(), getPoints());
	}
}
