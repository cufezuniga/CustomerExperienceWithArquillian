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
	
	public PurchaseOrder processOrder(String customerId, List<OrderItem> order) throws InvalidOrderException {
		/**
		 * 1. Lookup customer and account.
		 * 2. Get inventory for each of the items being purchased.
		 * 3. Check each item ordered against its inventory to determine if the quantity is 
		 *    sufficient.
		 *       If so, continue to line 4
		 *       If not, throw exception.
		 * 4. Create purchase order.
		 * 5. Persist purchase order and updating inventory with a transaction.
		 * 6. Email customer with order confirmation.
		 */
		
		List<Inventory> inventoryForOrder = inventoryService.getInventoryforOrder(order);
		
		order = purchaseOrderService.processOrderItems(order, inventoryForOrder);
		
		Customer customer = customerService.getCustomerAccount(customerId);
		
		PurchaseOrder po = purchaseOrderService.newPurchaseOrder(customer, order);
		
		finalizePurchase(po, inventoryForOrder);
		
		/** *******************************
		 * Other code like emailing customer with order confirmation, alerting order fulfillment system, etc.
		 */
		
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
