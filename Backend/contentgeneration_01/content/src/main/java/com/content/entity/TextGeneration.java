package com.content.entity;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
public class TextGeneration {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 5000)
	private String input;
	
	@Column(length = 5000)
	private String output;
	
	@OneToMany(cascade = CascadeType.ALL )
	List<History> historyList;
	
	@ManyToOne
	private Customer customer;
	
}
