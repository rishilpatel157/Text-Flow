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
import com.content.entity.TextGeneration;
import com.content.entity.TextSummarization;
import com.content.exception.InvalidUsernameException;
import com.content.repository.CustomerRepository;
import com.content.repository.TextSummRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TextSummServiceImpl implements TextSummService {

	@Value("${api.openai.secretkey}")
	private String secretKey;

	@Value("${api.openai.url}")
	private String url;

	RestTemplate restTemplate;
	ObjectMapper objectMapper;
	HttpHeaders httpHeaders;
	CustomerRepository customerRepository;
	TextSummRepository textSummRepository;
	
	

	@Autowired
	public TextSummServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper,
			HttpHeaders httpHeaders, CustomerRepository customerRepository, TextSummRepository textSummRepository) {
		super();
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		this.httpHeaders = httpHeaders;
		this.customerRepository = customerRepository;
		this.textSummRepository = textSummRepository;
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
		systemRole.put("content", "you have give a large document data you have to make in a most concise data make it as some data as possible only most important words");

		List<Map<String, String>> messages = new ArrayList<>();
		messages.add(systemRole);
		messages.add(userRole);

		Body body = new Body("gpt-3.5-turbo", messages, 200, 0.7, 0.9, 1.0, 1.0);

		HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(body), httpHeaders);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

		JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());
		String response = responseJson.get("choices").get(0).get("message").get("content").asText();

		
		String customerEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Customer customer = customerRepository.findByEmail(customerEmail)
				.orElseThrow(() -> new InvalidUsernameException("Invalid Email"));

		TextSummarization textSummarization = new TextSummarization();

		textSummarization.setInput(content);
		textSummarization.setOutput(response);
		textSummarization.setCustomer(customer);
		textSummRepository.save(textSummarization);
		
		return response;

	}

	@Override
	public List<TextSummarization> getAll(int id) {
		// TODO Auto-generated method stub
		
		List<TextSummarization> textGenerations = textSummRepository.findAll();
  List<TextSummarization> text = new ArrayList<>();
		  
		  for(TextSummarization i : textGenerations )
		  {
			  if(id ==  i.getCustomer().getId())
			  {
	               text.add(i);
			  }
		  }
		return text;
	}

}
