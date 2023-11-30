package com.contentgeneration_01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentgeneration_01.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	Optional<Customer>	findByEmail(String email);

}
