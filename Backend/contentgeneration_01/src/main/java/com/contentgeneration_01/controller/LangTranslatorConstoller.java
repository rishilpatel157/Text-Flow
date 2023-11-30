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

import com.contentgeneration_01.entity.LanguageTranslation;
import com.contentgeneration_01.entity.TextSummarization;
import com.contentgeneration_01.service.LangTranslatorService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class LangTranslatorConstoller {
	
	LangTranslatorService langTranslatorService;
	
	
	@Autowired
	public LangTranslatorConstoller(LangTranslatorService langTranslatorService) {
		super();
		this.langTranslatorService = langTranslatorService;
	}



	@PostMapping("/langtranslator/{inputlang}/{outputlang}")
	ResponseEntity<String> textGenerate(@RequestBody String content,@PathVariable String inputlang , @PathVariable String outputlang ) throws JsonProcessingException{
		
		
		return new ResponseEntity<String>(langTranslatorService.textGenerate(content, inputlang, outputlang), HttpStatus.OK);
		
	}
	
	@GetMapping("/langtranslator/{id}")
	ResponseEntity<List<LanguageTranslation>> getAll(@PathVariable  int id) throws JsonProcessingException {

		return new ResponseEntity<List<LanguageTranslation>>(langTranslatorService.getAll(id), HttpStatus.OK);

	}

}
