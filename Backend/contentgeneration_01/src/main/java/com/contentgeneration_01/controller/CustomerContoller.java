package com.contentgeneration_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.contentgeneration_01.entity.Customer;
import com.contentgeneration_01.service.CustomerService;

import jakarta.validation.Valid;

@RestController
public class CustomerContoller {

	CustomerService customerService;
	PasswordEncoder passwordEncoder;

	@Autowired
	public CustomerContoller(CustomerService customerService, PasswordEncoder passwordEncoder) {
		this.customerService = customerService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/customers")
	ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer) {

		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);

		return new ResponseEntity<String>(customerService.addCustomer(customer), HttpStatus.CREATED);
	}

	@GetMapping("/signIn")
	public ResponseEntity<String> getLoggedInCustomerDetailsHandler(Authentication auth) {

		Customer customer = customerService.getCustomerDetailsByEmail(auth.getName());

		return new ResponseEntity<String>(customer.getName() + " " + customer.getId(), HttpStatus.OK);
	}

}
