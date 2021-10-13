package demo.arquillian.monolith.inventory.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import demo.arquillian.monolith.commons.domain.OrderItem;
import demo.arquillian.monolith.inventory.dao.InventoryDAO;
import demo.arquillian.monolith.inventory.entity.Inventory;
import demo.arquillian.monolith.inventory.exception.InvalidOrderException;

@Stateless
@Named("InventoryService")
public class InventoryService {
	
	@Inject
	private InventoryDAO inventoryDao;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addInventoryItem(Inventory inventory) {
		inventoryDao.insertInventoryItem(inventory);
	}
	
	public Inventory findById(String id) {
		return inventoryDao.findById(id);
	}
	
	public List<Inventory> getAllInventory() {
		return inventoryDao.getAllInventory();
	}
	
	public Inventory getInventoryByItemId(String itemId) {
		return inventoryDao.getInventoryByItemid(itemId);
	}
	
	public List<Inventory> getInventoryforOrder(List<OrderItem> order) {
		return inventoryDao.getInventoryforOrder(order);
	}
	
	public void deleteInventory(Inventory inventory) {
		inventoryDao.deleteInventory(inventory);
	}
	
	public boolean updateInventory(Inventory inventory) {
		boolean success = false;
		
		return true;
	}
}
