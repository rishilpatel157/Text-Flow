package com.contentgeneration_01.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class TextSummarization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 5000)
	private String input;

	@Column(length = 5000)
	private String output;

	@ManyToOne
	private Customer customer;

	public TextSummarization() {
		super();
	}
	
	

}
