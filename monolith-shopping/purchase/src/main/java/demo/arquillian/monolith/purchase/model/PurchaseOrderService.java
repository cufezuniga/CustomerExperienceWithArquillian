package demo.arquillian.monolith.purchase.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import demo.arquillian.monolith.commons.domain.OrderItem;
import demo.arquillian.monolith.customer.entity.Customer;
import demo.arquillian.monolith.inventory.entity.Inventory;
import demo.arquillian.monolith.inventory.entity.Item;
import demo.arquillian.monolith.inventory.exception.InvalidOrderException;
import demo.arquillian.monolith.inventory.model.MessageHelper;
import demo.arquillian.monolith.purchase.dao.PurchaseOrderDAO;
import demo.arquillian.monolith.purchase.entity.ItemOrdered;
import demo.arquillian.monolith.purchase.entity.PurchaseOrder;

@Stateless
@Named("PurchaseOrderService")
public class PurchaseOrderService {

	@Inject
	PurchaseOrderDAO purchaseOrderDAO;
	
	
	public void savePurchaseOrder(PurchaseOrder order) {
		purchaseOrderDAO.savePurchaseOrder(order);
	}
	
	public PurchaseOrder getPurchaseOrder(String poid) {
		return purchaseOrderDAO.getPurchaseOrder(poid);
	}
	
	public PurchaseOrder newPurchaseOrder(Customer customer, List<OrderItem> order) {
		PurchaseOrder po = new PurchaseOrder(UUID.randomUUID().toString(), LocalDate.now());
		po.setCustomer(customer);
		List<ItemOrdered> itemsOrdered = order.stream().map(oi -> new ItemOrdered(UUID.randomUUID().toString(), (Item)oi.getItem(), po))
        .collect(Collectors.toList());
		
		po.setItems(itemsOrdered);
		
		return po;
	}
	
	/**
	 * Validate the order is valid and update the inventory for each item ordered if so.
	 *   
	 * Order validation:
	 * 1. Sufficient quantity in inventory for number of items ordered.
	 * 2. The item ordered is in the inventory.
	 *  
	 * @param order
	 * @param inventory
	 * @throws InvalidOrderException
	 */
	public List<OrderItem> processOrderItems(List<OrderItem> order, List<Inventory> inventory) throws InvalidOrderException {
		String errMsg = null;
		for(OrderItem it: order) {
			final String itemId = it.getItemId();
			Optional<Inventory> result = inventory.stream().filter(i -> i.getItem().getId().equals(itemId)).findFirst();
			if(result.isPresent()) {
				Inventory inv = result.get();
				if(it.getQuantity() < 1 || it.getQuantity() > inv.getQuantity()) {
					Object[] args = { MessageHelper.MSG_PREFIX_ERROR_HEADER, inv.getItem().getName(), inv.getQuantity() }; 
					errMsg = MessageHelper.formatMessage(
							MessageHelper.MSG_ERROR_INSUFFICIENT_QTY, args);
					throw new InvalidOrderException(errMsg);
				}
				
				// Update the inventory and assign item to ItemOrdered
				int newQty = inv.getQuantity() - it.getQuantity();
				inv.setQuantity(newQty);
				it.setItem(inv.getItem());
			}
			else {
				Object[] args = { MessageHelper.MSG_PREFIX_ERROR_HEADER, it};
				errMsg = MessageHelper.formatMessage(MessageHelper.MSG_ERROR_MISSING_INVENTORY, args); 
			}
		}	
		
		return order;
	} 
}
