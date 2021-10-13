package demo.arquillian.monolith.customer.interfaces;

import demo.arquillian.monolith.customer.entity.Customer;

public interface ICustomerRepository {

	public Customer getCustomerById(String customerId);
	
	public void createNewCustomerAccount(Customer customer);
}
