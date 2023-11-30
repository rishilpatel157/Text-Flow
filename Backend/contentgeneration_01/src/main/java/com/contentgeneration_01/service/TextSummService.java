package com.contentgeneration_01.service;

import java.util.List;

import com.contentgeneration_01.entity.TextGeneration;
import com.contentgeneration_01.entity.TextSummarization;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TextSummService {

	
	 String textGenerate(String content) throws JsonProcessingException;
	 
	 List<TextSummarization> getAll(int id);
		
}
