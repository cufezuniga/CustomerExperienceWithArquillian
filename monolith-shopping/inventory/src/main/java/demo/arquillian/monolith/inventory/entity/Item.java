package demo.arquillian.monolith.inventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "getItemsByIds", query = "select i from Item i where i.id in (:ids)"),
})

@Entity
@Table(name = "item", schema = "monolith")
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	private String name;
	private String description;
	private String brand;

	public Item() {
	}

	public Item(String id, String n, String desc, String brand) {
		this.id = id;
		this.setName(n);
		this.description = desc;
		this.brand = brand;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj != null) {
			if (obj instanceof Item) {
				Item i = (Item) obj;
				equals = this.getId().equals(i.getId());
			}
		}

		return equals;
	}

	@Override
	public String toString() {
		return String.format("Item [id: %s, name: %s, description: %s, brand: %s ]", id, name, description, brand);
	}
}
