package demo.arquillian.monolith.purchase.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import demo.arquillian.monolith.inventory.entity.Item;


@Entity
@Table(name = "item_ordered", schema = "monolith")
public class ItemOrdered {
	@Id
	@Column(name = "id")
	private String id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "itemId", referencedColumnName = "id")
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private PurchaseOrder purchaseOrder;
	
	private int quantity;
	
	public ItemOrdered() {
		
	}
	
	public ItemOrdered(String id) {
		this.id = id;
	}
	
	public ItemOrdered(String id, Item item, PurchaseOrder po) {
		this.id = id;
		this.item = item;
		this.purchaseOrder = po;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setPurchaseOrder(PurchaseOrder po) {
		this.purchaseOrder = po;
	}
	
	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}
	
	public void setQuantity(int qty) {
		this.quantity = qty;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public String toString() {
		return String.format("ItemOrdered [ id: '%s', item: %s, quantity: %d \n", id, item, quantity);   
	}
}
