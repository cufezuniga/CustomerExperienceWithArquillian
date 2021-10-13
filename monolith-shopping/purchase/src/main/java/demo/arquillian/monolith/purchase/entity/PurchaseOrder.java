package demo.arquillian.monolith.purchase.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import demo.arquillian.monolith.customer.entity.Customer;

//@NamedQueries({
//	@NamedQuery(name = "getAllGroupByCategory", query = "select i from Inventory i group by i.category, i.subCategory"),
//	@NamedQuery(name = "getInventoryforOrder", query = "select i from Inventory i where itemId in (:itemIds)")
//})

@Entity
@Table(name = "purchase_order", schema = "monolith")
public class PurchaseOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToMany(
		mappedBy = "purchaseOrder",
		fetch = FetchType.EAGER,
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<ItemOrdered> items;
	
	private LocalDate orderDate;
	
	public PurchaseOrder() {}
	
	public PurchaseOrder(String id, LocalDate od) {
		this.id = id;
		this.orderDate = od;
	}
	
	public String getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	public void setItems(List<ItemOrdered> items) {
		this.items = new ArrayList<ItemOrdered>(items);
	}
	
	public List<ItemOrdered> getItems() {
		return new ArrayList<ItemOrdered>(items);
	}
	
	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if(obj != null) {
			if(obj instanceof PurchaseOrder) {
				PurchaseOrder po = (PurchaseOrder)obj;
				equals = this.getId().equals(po.getId());
			}
		}
		
		return equals;
	}
	
	@Override
	public String toString() {
		return String.format("PurchaseOrder [ orderId: %s, customer: %s, orderDate: %s", id, customer.getName(), orderDate);
	}
	
}
