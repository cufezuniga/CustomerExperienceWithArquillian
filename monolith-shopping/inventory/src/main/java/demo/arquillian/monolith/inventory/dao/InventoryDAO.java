package demo.arquillian.monolith.inventory.dao;

import java.util.List;

import javax.inject.Inject;

import demo.arquillian.monolith.commons.domain.OrderItem;
import demo.arquillian.monolith.inventory.entity.Inventory;
import demo.arquillian.monolith.inventory.interfaces.IInventoryRepository;
import demo.arquillian.monolith.inventory.repository.JDBCInventoryRepository;

public class InventoryDAO {

	@Inject
	private IInventoryRepository inventoryRepository;

	public InventoryDAO() {
	}
	
	public void insertInventoryItem(Inventory inventory) {
		inventoryRepository.insertItem(inventory);
	}
	
	public Inventory findById(String id) {
		return inventoryRepository.findById(id);
	}
	
	public List<Inventory> getAllInventory() {
		return inventoryRepository.getInventory();
	}
	
	public Inventory getInventoryByItemid(String itemId) {
		return inventoryRepository.getInventoryByItemid(itemId);
	}
	
	public List<Inventory> getInventoryforOrder(List<OrderItem> order) {
		return inventoryRepository.getInventoryforOrder(order);
	}
	
	public Inventory updateInventory(Inventory inventory) {
		return inventoryRepository.updateInventory(inventory);
	}
	
	public void deleteInventory(Inventory inventory) {
		inventoryRepository.deleteInventory(inventory);
	}
}
