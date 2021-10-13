package demo.arquillian.monolith.purchase.repository;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import demo.arquillian.monolith.purchase.entity.PurchaseOrder;
import demo.arquillian.monolith.purchase.interfaces.IPurchaseOrderRepository;

@ApplicationScoped
public class JDBCPurchaseOrderRepository implements IPurchaseOrderRepository {

	@PersistenceContext(unitName = "monolith-shopping")
	private EntityManager entityManager;

	public PurchaseOrder getPurchaseOrder(String orderid) {
		PurchaseOrder po = entityManager.find(PurchaseOrder.class, orderid);
		return po;
	}

	public List<PurchaseOrder> getPurchaseOrder(String customerid, LocalDate purchaseDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	public void savePurchaseOrder(PurchaseOrder order) {
		System.out.printf("**==>> Save new PurchaseOrder: %s \n", order);
		entityManager.persist(order);
	}

}
