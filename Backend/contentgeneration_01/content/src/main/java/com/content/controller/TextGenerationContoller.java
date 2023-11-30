package com.content.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.content.entity.History;
import com.content.entity.TextGeneration;
import com.content.service.TextGenerationService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.annotation.PostConstruct;

@RestController
@CrossOrigin(origins = "*")
public class TextGenerationContoller {

	TextGenerationService textGenerationService;

	@Autowired
	public TextGenerationContoller(TextGenerationService textGenerationService) {
		super();
		this.textGenerationService = textGenerationService;
	}

	@PostMapping("/textgeneration")
	ResponseEntity<TextGeneration> textGenerate(@RequestBody String content) throws JsonProcessingException {

		return new ResponseEntity<TextGeneration>(textGenerationService.textGenerate(content), HttpStatus.OK);

	}

	@GetMapping("/textgeneratation/{id}")
	ResponseEntity<List<TextGeneration>> getAll(@PathVariable int id) throws JsonProcessingException {

		return new ResponseEntity<List<TextGeneration>>(textGenerationService.getAll(id), HttpStatus.OK);

	}
	
	@PostMapping("/textgen/{id}")
	ResponseEntity<String> textGen(@RequestBody String content,@PathVariable int id) throws JsonProcessingException {

		return new ResponseEntity<String>(textGenerationService.textGen(content,id), HttpStatus.OK);

	}
	
	
	@GetMapping("/history")
	ResponseEntity<List<TextGeneration>> getAllHistory()  {
		

		return new ResponseEntity<List<TextGeneration>>(textGenerationService.getAllHistory(), HttpStatus.OK);

	}
	
	@GetMapping("/history/{id}")
	ResponseEntity<List<History>> getHistoryId(@PathVariable int id)  {
		

		return new ResponseEntity<List<History>>(textGenerationService.getHistory(id), HttpStatus.OK);

	}
	
	
	

}