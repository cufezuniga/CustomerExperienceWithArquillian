package demo.arquillian.monolith.customer.model;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import demo.arquillian.monolith.customer.dao.CustomerDAO;
import demo.arquillian.monolith.customer.entity.Customer;

@Stateless
@Named("CustomerService")
public class CustomerService {

	@Inject CustomerDAO customerDao;
	
	public Customer getCustomerAccount(String customerId) {
		return customerDao.getCustomerById(customerId);
	}
	
	public void createNewCustomerAccount(Customer customer) {
		customerDao.createNewCustomerAccount(customer);
	}
}
