package demo.arquillian.monolith.inventory.interfaces;

import java.util.List;

import demo.arquillian.monolith.commons.domain.OrderItem;
import demo.arquillian.monolith.inventory.entity.Inventory;

public interface IInventoryRepository {
	
	public void insertItem(Inventory inventory);
	
	public Inventory findById(String id);
	
	public List<Inventory> getInventory();
	
	public Inventory getInventoryByItemid(String itemId);
	
	public List<Inventory> getInventoryforOrder(List<OrderItem> order);
	
	public Inventory updateInventory(Inventory inventory); 
	
	public void deleteInventory(Inventory inventory);
}
