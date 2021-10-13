package demo.arquillian.monolith.inventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import demo.arquillian.monolith.commons.domain.Category;
import demo.arquillian.monolith.commons.domain.SubCategory;

@NamedQueries({
	@NamedQuery(name = "getAllGroupByCategory", query = "select i from Inventory i group by i.category, i.subCategory"),
	@NamedQuery(name = "getInventoryByItemId", query = "select i from Inventory i where item.id = :itemId"),
	@NamedQuery(name = "getInventoryforOrder", query = "select i from Inventory i where i.item.id in (:itemIds)")
})

@Entity
@Table(name = "inventory", schema = "monolith")
public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;
	private Category category;
	private SubCategory subCategory;
	private int quantity;
	private double price;
	
	@OneToOne
	@JoinColumn(name = "itemId")
	private Item item;

	public Inventory() {}

	public Inventory(String id, Category category, SubCategory subCategory, int qty, double p) {
		this.id = id;
		this.category = category;
		this.subCategory = subCategory;
		this.quantity = qty;
		this.price = p;
	}
	
	public void setid(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setItem(Item i) {
		item = i;
	}
	
	public Item getItem() {
		return item;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String toString() {
		return String.format("Inventory [id: %s, [item: %s], category: %s, subCategory: %s, quantity: %d",
				id, item, category, subCategory, quantity);
	}
}
