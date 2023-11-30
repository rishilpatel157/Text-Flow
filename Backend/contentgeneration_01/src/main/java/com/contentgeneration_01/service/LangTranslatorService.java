package com.contentgeneration_01.service;

import java.util.List;

import com.contentgeneration_01.entity.LanguageTranslation;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface LangTranslatorService{

	  String textGenerate(String content,String inputLang,String outputLang) throws JsonProcessingException;
	  List<LanguageTranslation> getAll(int id);
	  
	  
	
}
