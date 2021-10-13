package demo.arquillian.monolith.inventory.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import demo.arquillian.monolith.commons.domain.OrderItem;
import demo.arquillian.monolith.commons.utils.QueryHelper;
import demo.arquillian.monolith.inventory.entity.Inventory;
import demo.arquillian.monolith.inventory.interfaces.IInventoryRepository;

@ApplicationScoped
public class JDBCInventoryRepository implements IInventoryRepository {

	@PersistenceContext(unitName = "monolith-shopping")
	private EntityManager entityManager;
		
	@Override
	public void insertItem(Inventory inventory) {
		entityManager.persist(inventory);
	}
	
	@Override
	public Inventory findById(String id) {
		Inventory inv =  entityManager.find(Inventory.class, id);
		return inv;
	}

	@Override
	public List<Inventory> getInventory() {
		TypedQuery<Inventory> query = entityManager.createNamedQuery("getAllGroupByCategory", Inventory.class);
		List<Inventory> invs = query.getResultList();
		
		return invs;
	}

	@Override
	public Inventory getInventoryByItemid(String itemId) {
		TypedQuery<Inventory> query = entityManager.createNamedQuery("getInventoryByItemId", Inventory.class);
		query.setParameter("itemId", itemId);
		return query.getSingleResult();
	}
	
	@Override
	public List<Inventory> getInventoryforOrder(List<OrderItem> order) {
		TypedQuery<Inventory> query = entityManager.createNamedQuery("getInventoryforOrder", Inventory.class);
		query.setParameter("itemIds", QueryHelper.getIdsFromOrderItem(order));
		List<Inventory> invs = query.getResultList();
		
		return invs;
	}
	
	@Override
	public Inventory updateInventory(Inventory inventory) {
		return (Inventory)entityManager.merge(inventory);
	}	
	
	@Override
	public void deleteInventory(Inventory inventory) {
		entityManager.remove(inventory);
	}
}
