package com.content.service;

import java.util.List;

import com.content.entity.LanguageTranslation;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface LangTranslatorService{

	  String textGenerate(String content,String inputLang,String outputLang) throws JsonProcessingException;
	  List<LanguageTranslation> getAll(int id);
	  
	  
	
}
