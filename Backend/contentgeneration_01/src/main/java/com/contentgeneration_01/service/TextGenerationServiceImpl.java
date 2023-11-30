package com.contentgeneration_01.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.contentgeneration_01.entity.Customer;
import com.contentgeneration_01.entity.TextGeneration;
import com.contentgeneration_01.entity.TextSummarization;
import com.contentgeneration_01.exception.InvalidUsernameException;
import com.contentgeneration_01.repository.CustomerRepository;
import com.contentgeneration_01.repository.TextGenerationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TextGenerationServiceImpl implements TextGenerationService {

	@Value("${api.openai.secretkey}")
	private String secretKey;

	@Value("${api.openai.url}")
	private String url;

	RestTemplate restTemplate;
	ObjectMapper objectMapper;
	HttpHeaders httpHeaders;
	CustomerRepository customerRepository;
	TextGenerationRepository textGenerationRepository;

	@Autowired
	public TextGenerationServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper, HttpHeaders httpHeaders,
			CustomerRepository customerRepository, TextGenerationRepository textGenerationRepository) {
		super();
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		this.httpHeaders = httpHeaders;
		this.customerRepository = customerRepository;
		this.textGenerationRepository = textGenerationRepository;
	}

	@Override
	public String textGenerate(String content) throws JsonProcessingException {

		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", "Bearer " + secretKey);

		Map<String, String> userRole = new HashMap<>();
		userRole.put("role", "user");
		userRole.put("content", content);

		Map<String, String> systemRole = new HashMap<>();
		systemRole.put("role", "system");
		systemRole.put("content", "you are a content creator who generates creative and "
				+ "contextually relevant content in a normal human text");

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

		TextGeneration textGeneration = new TextGeneration();

		textGeneration.setInput(content);
		textGeneration.setOutput(response);
		textGeneration.setCustomer(customer);
		textGenerationRepository.save(textGeneration);
		return response;

	}

	@Override
	public List<TextGeneration> getAll(int id){
		List<TextGeneration> textGenerations = textGenerationRepository.findAll();
        List<TextGeneration> text = new ArrayList<>();
		  
		  for(TextGeneration i : textGenerations )
		  {
			  if(id ==  i.getCustomer().getId())
			  {
	               text.add(i);
			  }
		  }
		return text;
	}

}
