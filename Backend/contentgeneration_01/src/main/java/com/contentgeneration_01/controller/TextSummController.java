package com.contentgeneration_01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.contentgeneration_01.entity.TextGeneration;
import com.contentgeneration_01.entity.TextSummarization;
import com.contentgeneration_01.service.TextGenerationService;
import com.contentgeneration_01.service.TextSummService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class TextSummController {

	
	TextSummService textSummService;
	
    @Autowired
 	public TextSummController(TextSummService textSummService) {
		super();
		this.textSummService = textSummService;
	}







	@PostMapping("/textsumm")
	ResponseEntity<String> textGenerate(@RequestBody String content) throws JsonProcessingException{
		
		
		return new ResponseEntity<String>(textSummService.textGenerate(content), HttpStatus.OK);
		
	}
	

	@GetMapping("/textsumm/{id}")
	ResponseEntity<List<TextSummarization>> getAll( @PathVariable  int id) throws JsonProcessingException {

		return new ResponseEntity<List<TextSummarization>>(textSummService.getAll(id), HttpStatus.OK);

	}
}
