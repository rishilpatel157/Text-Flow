package com.contentgeneration_01.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.contentgeneration_01.entity.Authority;
import com.contentgeneration_01.entity.Customer;
import com.contentgeneration_01.exception.InvalidUsernameException;
import com.contentgeneration_01.repository.CustomerRepository;

@Service
public class UserDetail implements UserDetailsService{
	
	CustomerRepository customerRepository;
	

	public UserDetail() {
		super();
	}



  @Autowired
	public UserDetail(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> opt= customerRepository.findByEmail(username);

		Customer customer = null;
		try {
			customer = opt.orElseThrow(()-> new InvalidUsernameException("Email is Invalid"));
		} catch (InvalidUsernameException e) {
			e.printStackTrace();
		}
	
		List<Authority> auths = customer.getAuthority();
List<GrantedAuthority> authorities = new ArrayList<>();
		
		for (Authority auth : auths) {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(auth.getName());
			authorities.add(sga);
		}
	            	
			return new User(customer.getEmail(), customer.getPassword(), authorities);
	}
	

}
