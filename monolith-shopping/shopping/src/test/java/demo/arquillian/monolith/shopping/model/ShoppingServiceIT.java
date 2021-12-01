package demo.arquillian.monolith.shopping.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import demo.arquillian.monolith.commons.domain.Category;
import demo.arquillian.monolith.commons.domain.OrderItem;
import demo.arquillian.monolith.commons.domain.SubCategory;
import demo.arquillian.monolith.commons.utils.MessageHelper;
import demo.arquillian.monolith.commons.utils.QueryHelper;
import demo.arquillian.monolith.customer.dao.CustomerDAO;
import demo.arquillian.monolith.customer.entity.Account;
import demo.arquillian.monolith.customer.entity.Customer;
import demo.arquillian.monolith.customer.interfaces.ICustomerRepository;
import demo.arquillian.monolith.customer.model.CustomerService;
import demo.arquillian.monolith.customer.repository.JDBCCustomerRepository;
import demo.arquillian.monolith.inventory.dao.InventoryDAO;
import demo.arquillian.monolith.inventory.entity.Inventory;
import demo.arquillian.monolith.inventory.entity.Item;
import demo.arquillian.monolith.purchase.exception.InvalidOrderException;
import demo.arquillian.monolith.inventory.interfaces.IInventoryRepository;
import demo.arquillian.monolith.inventory.model.InventoryService;
import demo.arquillian.monolith.inventory.repository.JDBCInventoryRepository;
import demo.arquillian.monolith.purchase.dao.PurchaseOrderDAO;
import demo.arquillian.monolith.purchase.entity.ItemOrdered;
import demo.arquillian.monolith.purchase.entity.PurchaseOrder;
import demo.arquillian.monolith.purchase.interfaces.IPurchaseOrderRepository;
import demo.arquillian.monolith.purchase.model.PurchaseOrderService;
import demo.arquillian.monolith.purchase.repository.JDBCPurchaseOrderRepository;

@RunWith(Arquillian.class)
public class ShoppingServiceIT {

	@Deployment
	public static Archive<?> createDeployment() {
		Archive<?> warDeployment = ShrinkWrap.create(WebArchive.class, "shopping-test.war")
				// **** Dependencies for shopping-service ****
				.addClass(ShoppingService.class)
				
				// **** Dependencies for inventory-service
				.addClass(InventoryService.class)
				.addClass(InventoryDAO.class)
				.addClass(Inventory.class)
				.addClass(Item.class)
				.addClass(IInventoryRepository.class)
				.addClass(JDBCInventoryRepository.class)
				
				// **** Dependencies for customer-service
				.addClass(CustomerService.class)
				.addClass(CustomerDAO.class)
				.addClass(Customer.class)
				.addClass(Account.class)
				.addClass(ICustomerRepository.class)
				.addClass(JDBCCustomerRepository.class)
				
				// **** Dependencies for commons
				.addClass(OrderItem.class)
				.addClass(SubCategory.class)
				.addClass(Category.class)
				.addClass(QueryHelper.class)
				.addClass(MessageHelper.class)
				
				/** All dependencies from the purchase module.
				 * Add all classes with one fell swoop to save time trying to include
				 * all the classes needed to get a monolith application to build.
				**/
				.addPackages(true, "demo.arquillian.monolith.purchase")

				// Use the persistence context configurad in src/test/resources rather than the one in 
				// src/main/resources
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		return warDeployment;
	}

	@Inject
	private ShoppingService shoppingService;

	@Test
	public void testValidProcessOrder() {
		List<OrderItem> itemsOrdered = createValidOrderItems();
		try {
			PurchaseOrder po = shoppingService.processOrder("c-12345", itemsOrdered);

			String poid = po.getId();
			PurchaseOrder poNew = shoppingService.getPurchaseOrder(poid);

			assertNotNull("Missing Purchase Order", poNew);
			assertEquals("The new purchase order not found.", poid, poNew.getId());
			assertEquals("Two items should have been purchased.", 2, poNew.getItems().size());
		} catch (InvalidOrderException ioe) {
			String error = String.format("Invalid order: error='%s' \n", ioe.getMessage());
			fail(error);
		} catch (Exception ex) {
			String error = String.format("test failed: error='%s' \n", ex.getMessage());
			fail(error);
		}
	}

	public void testInvalidProcessOrder() {
		List<OrderItem> itemsOrdered = createInvalidOrderItems();
		PurchaseOrder po;
		try {
			po = shoppingService.processOrder("c-12345", itemsOrdered);
			fail("An InvalidOrderException should have been thrown here.");
		} 
		catch (InvalidOrderException e) {
		}
	}

	private List<OrderItem> createValidOrderItems() {
		List<OrderItem> order = new ArrayList<>(2);
		order.add(new OrderItem("i-8907654", 3));
		order.add(new OrderItem("i-8888763", 1));

		return order;
	}

	private List<OrderItem> createInvalidOrderItems() {
		List<OrderItem> order = new ArrayList<>(2);
		order.add(new OrderItem("i-8907654", 65));
		order.add(new OrderItem("i-8888763", 1));

		return order;
	}
}
