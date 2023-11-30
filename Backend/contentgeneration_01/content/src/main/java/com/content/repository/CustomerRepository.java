package com.content.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.content.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	Optional<Customer>	findByEmail(String email);

}

