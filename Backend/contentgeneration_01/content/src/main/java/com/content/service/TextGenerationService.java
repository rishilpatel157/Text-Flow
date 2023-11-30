package com.content.service;
import java.util.List;

import com.content.entity.History;
import com.content.entity.TextGeneration;
import com.content.entity.TextSummarization;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TextGenerationService {

	TextGeneration textGenerate(String content) throws JsonProcessingException;
	
	 List<TextGeneration> getAll(int id);
	 
	 String textGen(String content,int id) throws JsonProcessingException;
	 
	List<TextGeneration> getAllHistory();
	
	List<History> getHistory(int id);
}
