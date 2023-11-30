package com.contentgeneration_01.service;

import com.contentgeneration_01.entity.Customer;

public interface CustomerService {
	
Customer getCustomerDetailsByEmail(String email);
	
	String addCustomer(Customer customer);

}
