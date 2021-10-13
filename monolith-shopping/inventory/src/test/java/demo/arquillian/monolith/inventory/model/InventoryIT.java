package demo.arquillian.monolith.inventory.model; 

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import demo.arquillian.monolith.commons.domain.Category;
import demo.arquillian.monolith.commons.domain.OrderItem;
import demo.arquillian.monolith.commons.domain.SubCategory;
import demo.arquillian.monolith.inventory.dao.InventoryDAO;
import demo.arquillian.monolith.inventory.entity.Inventory;
import demo.arquillian.monolith.inventory.entity.Item;
import demo.arquillian.monolith.inventory.interfaces.IInventoryRepository;
import demo.arquillian.monolith.inventory.repository.JDBCInventoryRepository;
/**
 * A simple test case using Arquillian to test an EJB.
 *
 */
@RunWith(Arquillian.class)
public class InventoryIT {

    @Deployment
    public static Archive<?> createDeployment() {
    	Archive<?> artifact = ShrinkWrap.create(WebArchive.class, "inventory-test.war")
    		.addClass(InventoryService.class)
    		.addClass(InventoryDAO.class)
    		.addClass(Inventory.class)
    		.addClass(Item.class)
    		.addClass(Category.class)
    		.addClass(SubCategory.class)
    		.addClass(OrderItem.class)
    		.addClass(IInventoryRepository.class)
    		.addClass(JDBCInventoryRepository.class)
        	.addAsResource("META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        
        return artifact;
    }

    @Inject
    private InventoryService inventoryService;

    @Test
    public void testGetAllInventory() {
    	List<Inventory> invs = inventoryService.getAllInventory();
    	
    	assertEquals("The inventory should be 5.", 5, invs.size());
    }
}
