package com.content.service;

import java.util.List;

import com.content.entity.TextGeneration;
import com.content.entity.TextSummarization;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TextSummService {

	
	 String textGenerate(String content) throws JsonProcessingException;
	 
	 List<TextSummarization> getAll(int id);
		
}
