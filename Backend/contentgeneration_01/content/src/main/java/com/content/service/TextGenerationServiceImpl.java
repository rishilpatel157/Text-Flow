package com.content.service;

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

import com.content.entity.Customer;
import com.content.entity.History;
import com.content.entity.TextGeneration;
import com.content.entity.TextSummarization;
import com.content.exception.InvalidUsernameException;
import com.content.repository.CustomerRepository;
import com.content.repository.HistoryRepository;
import com.content.repository.TextGenerationRepository;
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
	HistoryRepository historyRepository;

	

	public TextGenerationServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper, HttpHeaders httpHeaders,
			CustomerRepository customerRepository, TextGenerationRepository textGenerationRepository,
			HistoryRepository historyRepository) {
		super();
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		this.httpHeaders = httpHeaders;
		this.customerRepository = customerRepository;
		this.textGenerationRepository = textGenerationRepository;
		this.historyRepository = historyRepository;
	}

	@Override
	public TextGeneration textGenerate(String content) throws JsonProcessingException {

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

		Body body = new Body("gpt-3.5-turbo", messages, 100, 1.1, 0.9, 1.0, 1.0);

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

		    // Save the TextGeneration entity to the database
		    textGenerationRepository.save(textGeneration);

		    // Now that the TextGeneration is saved, create and set the History entity
		    History history = new History();
		    history.setInput(content);
		    history.setOutput(response);
		    history.setTextGeneration(textGeneration); // Set the association to the saved TextGeneration
		    historyRepository.save(history);

		return textGeneration;

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

	@Override
	public String textGen(String content,int id) throws JsonProcessingException {
		TextGeneration textGeneration  = new TextGeneration();
		String customerEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Customer customer = customerRepository.findByEmail(customerEmail)
				.orElseThrow(() -> new InvalidUsernameException("Invalid Email"));
		
		List<History> hitory = historyRepository.findAll();
	
		List<History> filtered = new ArrayList<>();
		
		List<TextGeneration> textGenerationsList = textGenerationRepository.findAll();
		
		List<TextGeneration> textGenerationsfiltered = new ArrayList<>();
		
		for(TextGeneration i : textGenerationsList)
		{
			if(i.getCustomer().getId() == customer.getId())
			{
				textGenerationsfiltered.add(i);
			}
		}
		
		for(TextGeneration i : textGenerationsfiltered)
		{
			if(i.getId() == id)
			{
				textGeneration = i;
			}
		}
		
		System.out.println(textGeneration+" ----------");
		
		
		
		for(History i : hitory)
		{
			if(i.getTextGeneration().getId() == id)
			{
				filtered.add(i);
			}
		}
		
		
		List<String> chatList = new ArrayList<>();
		
		for(History i : filtered) {
			chatList.add(i.getInput()+" - ");
			chatList.add(i.getOutput()+" - ");
		}
		
		String chats = String.join(" - " , chatList);
		
		
		
				System.out.println(chats+" ====================");
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", "Bearer " + secretKey);
		

		Map<String, String> userRole = new HashMap<>();
		userRole.put("role", "user");
		userRole.put("content","our conversations - "+chats+" give me answers accordingly this is the current question -> "+ content);

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

	
	
        
	    textGenerationRepository.save(textGeneration);
	    
	    History history = new History();
	    history.setInput(content);
       history.setOutput(response);
       history.setTextGeneration(textGeneration);	    
     
       historyRepository.save(history);

 
    return response;
	}

	@Override
	public List<TextGeneration> getAllHistory(){
		// TODO Auto-generated method stub
//		System.out.println("asfdsadf");
		String customerEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Customer customer = customerRepository.findByEmail(customerEmail)
				.orElseThrow(() -> new InvalidUsernameException("Invalid Email"));
		
List<TextGeneration> textGenerationsList = textGenerationRepository.findAll();
		
		List<TextGeneration> textGenerationsfiltered = new ArrayList<>();
		
		for(TextGeneration i : textGenerationsList)
		{
			if(i.getCustomer().getId() == customer.getId())
			{
				textGenerationsfiltered.add(i);
			}
		}
		
		
		return textGenerationsfiltered;
	}

	@Override
	public List<History> getHistory(int id) {
		
//		TextGeneration textGeneration = textGenerationRepository.findById(id).orElseThrow(() -> new InvalidUsernameException("Invalid "));
	List<History> history = historyRepository.findAll();
	List<History> filtered = new ArrayList<>();
	            for(History i : history) {
	           	if(i.getTextGeneration().getId() == id) {
	            		
	            		filtered.add(i);
	            	}
	            }
	
		return filtered;               
		 
	}

}
