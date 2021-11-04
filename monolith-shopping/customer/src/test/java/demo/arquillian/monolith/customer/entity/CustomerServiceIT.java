package demo.arquillian.monolith.customer.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import demo.arquillian.monolith.customer.dao.CustomerDAO;
import demo.arquillian.monolith.customer.interfaces.ICustomerRepository;
import demo.arquillian.monolith.customer.model.CustomerService;
import demo.arquillian.monolith.customer.repository.JDBCCustomerRepository;

@RunWith(Arquillian.class)
public class CustomerServiceIT {

	@Inject
	private CustomerService customerService;
	
	@Deployment
    public static Archive<?> createDeployment() {
    	Archive<?> artifact = ShrinkWrap.create(WebArchive.class, "customer-test.war")
    		.addClass(CustomerService.class)
    		.addClass(Customer.class)
    		.addClass(Account.class)
    		.addClass(CustomerDAO.class)
    		.addClass(ICustomerRepository.class)
    		.addClass(JDBCCustomerRepository.class)
        	.addAsResource("META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        
        return artifact;
    }
	
	@Test
	public void testCreateCustomer() {
		Customer customer = createNewAccount();
		customerService.createNewCustomerAccount(customer);
		
		String id = customer.getId();
		Customer savedCustomer = customerService.getCustomerAccount(id);
		assertNotNull(savedCustomer);
		
		Account account = savedCustomer.getAccount();
		assertNotNull(account);
		
		
		customerService.deleteCustomerAccount(savedCustomer);
		Customer removedCustomer = customerService.getCustomerAccount(id);
		assertNull("Customer '" + id +"' should be deleted.", removedCustomer);
	}
	
	private Customer createNewAccount() {
		Account account = new Account();
		account.setId(UUID.randomUUID().toString());
		account.setLastPurchase(LocalDate.of(2021, 1, 10));
		account.setPoints(128);
		account.setStartDate(LocalDate.of(2017, 5, 11));
		
		Customer customer = new Customer(UUID.randomUUID().toString(), "Steven", "LaBrae", "3145 Maple St.", "Tulsa", "OK", "74154", "9187769848");
		customer.setAccount(account);
		
		return customer;
	}
}
