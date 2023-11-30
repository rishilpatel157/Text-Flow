package com.content.service;
import com.content.entity.Customer;

public interface CustomerService {
	
Customer getCustomerDetailsByEmail(String email);
	
	String addCustomer(Customer customer);

}
