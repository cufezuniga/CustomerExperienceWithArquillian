package demo.arquillian.monolith.customer.dao;

import javax.inject.Inject;

import demo.arquillian.monolith.customer.entity.Customer;
import demo.arquillian.monolith.customer.interfaces.ICustomerRepository;

public class CustomerDAO {

	@Inject
	private ICustomerRepository customerRepository;
	
	public CustomerDAO() {
		
	}
	
	public Customer getCustomerById(String customerId) {
		return customerRepository.getCustomerById(customerId);
	}
	
	public void createNewCustomerAccount(Customer customer) {
		customerRepository.createNewCustomerAccount(customer);
	}
}
