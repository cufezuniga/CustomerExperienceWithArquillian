package demo.arquillian.monolith.purchase.interfaces;

import java.time.LocalDate;
import java.util.List;

import demo.arquillian.monolith.purchase.entity.PurchaseOrder;

public interface IPurchaseOrderRepository {

	public PurchaseOrder getPurchaseOrder(String orderid);
	
	public List<PurchaseOrder> getPurchaseOrder(String customerid, LocalDate purchaseDate);
	
	public void savePurchaseOrder(PurchaseOrder order);
}
