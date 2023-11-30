package com.contentgeneration_01.service;

import org.springframework.stereotype.Service;

import com.contentgeneration_01.entity.Customer;
import com.contentgeneration_01.exception.InvalidUsernameException;
import com.contentgeneration_01.repository.CustomerRepository;

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
