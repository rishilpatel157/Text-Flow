package com.contentgeneration_01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.contentgeneration_01.entity.TextGeneration;
import com.contentgeneration_01.service.TextGenerationService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.annotation.PostConstruct;

@RestController
public class TextGenerationContoller {

	TextGenerationService textGenerationService;

	@Autowired
	public TextGenerationContoller(TextGenerationService textGenerationService) {
		super();
		this.textGenerationService = textGenerationService;
	}

	@PostMapping("/textgeneration")
	ResponseEntity<String> textGenerate(@RequestBody String content) throws JsonProcessingException {

		return new ResponseEntity<String>(textGenerationService.textGenerate(content), HttpStatus.OK);

	}

	@GetMapping("/textgeneratation/{id}")
	ResponseEntity<List<TextGeneration>> getAll(@PathVariable int id) throws JsonProcessingException {

		return new ResponseEntity<List<TextGeneration>>(textGenerationService.getAll(id), HttpStatus.OK);

	}

}
