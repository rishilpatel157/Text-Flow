package com.content.service;


import org.springframework.stereotype.Service;

import com.content.entity.Customer;
import com.content.exception.InvalidUsernameException;
import com.content.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public Customer getCustomerDetailsByEmail(String email) {
		return customerRepository.findByEmail(email).orElseThrow(() -> new InvalidUsernameException("Email not Found"));
	}

	@Override
	public String addCustomer(Customer customer) {

		customerRepository.save(customer);
		return "Registered Successfully";
	}

}
