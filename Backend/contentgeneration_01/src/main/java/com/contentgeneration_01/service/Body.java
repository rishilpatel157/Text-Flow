package com.contentgeneration_01.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Body {
	
	 @JsonProperty("model")
	    private String model;

	    @JsonProperty("messages")
	   private List<Map<String, String>> messages;
	   
	    @JsonProperty("max_tokens")
	    private int max_tokens;
	    @JsonProperty("temperature")
	    private double temperature;
	    
	    @JsonProperty("top_p")
	    private double  top_p;
	    @JsonProperty("frequency_penalty")
	    private double frequency_penalty;
	    
	    @JsonProperty("presence_penalty")
	    private double presence_penalty;
	    
		public Body(String model, List<Map<String, String>> messages, int max_tokens, double temperature, double top_p,
				double frequency_penalty, double presence_penalty) {
			super();
			this.model = model;
			this.messages = messages;
			this.max_tokens = max_tokens;
			this.temperature = temperature;
			this.top_p = top_p;
			this.frequency_penalty = frequency_penalty;
			this.presence_penalty = presence_penalty;
		}
		
		
		

	 

}
