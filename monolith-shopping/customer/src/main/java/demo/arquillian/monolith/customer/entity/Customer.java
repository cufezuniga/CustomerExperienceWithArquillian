package demo.arquillian.monolith.customer.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "customer", schema = "monolith")
public class Customer implements Serializable {

	private static final long serialVersionUID = 2629211411604084274L;
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "fname")
	private String firstName;
	
	@Column(name = "lname")
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "accountId")
	private Account account;
	
	public Customer() {
		
	}
	
	public Customer(String id, String fname, String lname, String addr, String c, String st, String z, String ph) {
		this.id = id;
		this.firstName = fname;
		this.lastName = lname;
		this.address = addr;
		this.city = c;
		this.state = st;
		this.zip = z;
		this.phone = ph;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setFirstName(String fn) {
		this.firstName = fn;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setLastName(String ln) {
		lastName = ln;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getZip() {
		return zip;
	}

	public void setZip(String z) {
		this.zip = z;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setAccount(Account acct) {
		this.account = acct;
	}
	
	public Account getAccount() {
		return account;
	}
	
	@Transient
	public String getName() {
		return firstName + " " + lastName;
	}
	
	@Transient
	public String getFullAddress() {
		return address + ", " + city + " " + state + " " + zip;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if( obj != null) {
			if(obj instanceof Customer) {
				Customer cust = (Customer)obj;
				equals = this.getId().equals(cust.getId());
			}
		}
			
		return equals;
		
	}
	
	@Override
	public String toString() {
		String str = String.format("Customer [ id: %s, name: %s, address: %s, phone: %s ]", getId(), getName(), getFullAddress(), getPhone());
		str += "\n -- " + getAccount();
		
		return str;
	}
}
