package demo.arquillian.monolith.customer.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import demo.arquillian.monolith.customer.entity.Customer;
import demo.arquillian.monolith.customer.interfaces.ICustomerRepository;

@ApplicationScoped
public class JDBCCustomerRepository implements ICustomerRepository {

	@PersistenceContext(unitName = "monolith-shopping")
	private EntityManager entityManager;

	@Override
	public Customer getCustomerById(String customerId) {
		return entityManager.find(Customer.class, customerId);
	}

	public void createNewCustomerAccount(Customer customer) {
		entityManager.persist(customer);
	}

	public void deleteCustomerAccount(Customer customer) {
		if(!entityManager.contains(customer)) {
			customer = entityManager.merge(customer);
		}
		entityManager.remove(customer);
	}
}
