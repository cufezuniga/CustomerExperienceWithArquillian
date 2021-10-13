package demo.arquillian.monolith.purchase.dao;

import javax.inject.Inject;

import demo.arquillian.monolith.purchase.entity.PurchaseOrder;
import demo.arquillian.monolith.purchase.interfaces.IPurchaseOrderRepository;

public class PurchaseOrderDAO {

	@Inject
	private IPurchaseOrderRepository purchaseOrderRepository;
	
	public void savePurchaseOrder(PurchaseOrder order) {
		purchaseOrderRepository.savePurchaseOrder(order);
	}
	
	public PurchaseOrder getPurchaseOrder(String poid) {
		return purchaseOrderRepository.getPurchaseOrder(poid);
	}
}
