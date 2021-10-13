package demo.arquillian.monolith.commons.domain;

public class OrderItem {
	private String itemId;
	private int quantity;
	
	private Object item;
	
	public OrderItem(String id, int qty) {
		itemId = id;
		quantity = qty;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setItem(Object item) {
		this.item = item;
	}
	
	public Object getItem() {
		return item;
	}
}
