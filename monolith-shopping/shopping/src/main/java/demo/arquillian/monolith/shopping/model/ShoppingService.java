package demo.arquillian.monolith.shopping.model;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import demo.arquillian.monolith.commons.domain.OrderItem;
import demo.arquillian.monolith.customer.entity.Customer;
import demo.arquillian.monolith.customer.model.CustomerService;
import demo.arquillian.monolith.inventory.entity.Inventory;
import demo.arquillian.monolith.inventory.model.InventoryService;
import demo.arquillian.monolith.purchase.entity.PurchaseOrder;
import demo.arquillian.monolith.purchase.exception.InvalidOrderException;
import demo.arquillian.monolith.purchase.model.PurchaseOrderService;

@Stateless
@Named("ShoppingService")
public class ShoppingService {

	@EJB
	private InventoryService inventoryService;
	
	@EJB 
	private CustomerService customerService;
	
	@EJB
	private PurchaseOrderService purchaseOrderService;

	/**
	 * A small example of a legacy monolith that was partially refactored into a maven project with a parent BOM 
	 * and child modules. Below is a small example of a external service (API) that was refactored as much as possible. It has
	 * references to several other classes that were also refactored. The service call below is tested with arquillian
	 * to verify the code still behaves as it should and detects any problems in any of the refactored code. 
	 */
	public PurchaseOrder processOrder(String customerId, List<OrderItem> order) throws InvalidOrderException {
		List<Inventory> inventoryForOrder = inventoryService.getInventoryforOrder(order);
		
		order = purchaseOrderService.processOrderItems(order, inventoryForOrder);
		
		Customer customer = customerService.getCustomerAccount(customerId);
		
		PurchaseOrder po = purchaseOrderService.newPurchaseOrder(customer, order);
		
		finalizePurchase(po, inventoryForOrder);
		
		return po;
	}	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void finalizePurchase(PurchaseOrder order, List<Inventory> inventory) {
		purchaseOrderService.savePurchaseOrder(order);
		
		inventory.forEach(inv -> inventoryService.updateInventory(null));
	}
	
	public PurchaseOrder getPurchaseOrder(String poid) {
		return purchaseOrderService.getPurchaseOrder(poid);
	}
}
