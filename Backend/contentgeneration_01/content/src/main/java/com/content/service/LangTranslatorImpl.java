package com.content.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.content.entity.Customer;
import com.content.entity.LanguageTranslation;
import com.content.entity.TextGeneration;
import com.content.entity.TextSummarization;
import com.content.exception.InvalidUsernameException;
import com.content.repository.CustomerRepository;
import com.content.repository.LangTranslatorRepository;
import com.content.repository.TextGenerationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LangTranslatorImpl implements LangTranslatorService {

	@Value("${api.openai.secretkey}")
	private String secretKey;

	@Value("${api.openai.url}")
	private String url;

	RestTemplate restTemplate;
	ObjectMapper objectMapper;
	HttpHeaders httpHeaders;
	CustomerRepository customerRepository;
	LangTranslatorRepository langTranslatorRepository;

	@Autowired
	public LangTranslatorImpl(RestTemplate restTemplate, ObjectMapper objectMapper, HttpHeaders httpHeaders,
			CustomerRepository customerRepository, LangTranslatorRepository langTranslatorRepository) {
		super();
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		this.httpHeaders = httpHeaders;
		this.customerRepository = customerRepository;
		this.langTranslatorRepository = langTranslatorRepository;
	}

	@Override
	public String textGenerate(String content, String inputLang, String outputLang) throws JsonProcessingException {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", "Bearer " + secretKey);

		Map<String, String> userRole = new HashMap<>();
		userRole.put("role", "user");
		userRole.put("content", "Translate from " + inputLang + " content " + content + " to " + outputLang+" also includes Sentiment and emotions");

		Map<String, String> systemRole = new HashMap<>();
		systemRole.put("role", "system");
		systemRole.put("content", "you are a language Translator");

		List<Map<String, String>> messages = new ArrayList<>();
		messages.add(systemRole);
		messages.add(userRole);

		Body body = new Body("gpt-3.5-turbo", messages, 100, 0.7, 0.9, 1.0, 1.0);

		HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(body), httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

		JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());
		String response = responseJson.get("choices").get(0).get("message").get("content").asText();

		String customerEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Customer customer = customerRepository.findByEmail(customerEmail)
				.orElseThrow(() -> new InvalidUsernameException("Invalid Email"));

		LanguageTranslation languageTranslation = new LanguageTranslation();

		languageTranslation.setInput(content);
		languageTranslation.setInputLanguage(inputLang);
		languageTranslation.setOutputLanguage(outputLang);
		languageTranslation.setOutput(response);
		languageTranslation.setCustomer(customer);

		langTranslatorRepository.save(languageTranslation);
		return response;

	}

	@Override
	public List<LanguageTranslation> getAll(int id) {
		List<LanguageTranslation> languageTranslations = langTranslatorRepository.findAll();
  List<LanguageTranslation> text = new ArrayList<>();
		  
		  for(LanguageTranslation i : languageTranslations )
		  {
			  if(id ==  i.getCustomer().getId())
			  {
	               text.add(i);
			  }
		  }
		return text;
	}

}
