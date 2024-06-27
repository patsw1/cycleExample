package com.example.cycleExample.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

public class BasicService {
	private static Logger logger = LogManager.getLogger();

	public Flux<Object> basic() {
		ObjectMapper objectMapper = new ObjectMapper();
		String s = MyJson.get();
		logger.info(s);
		try {
			Object object = objectMapper.readValue(s,Object.class);
			Flux<Object> flux = Flux.just(object);
			return flux;
		} catch (JsonMappingException e) {
			Flux<Object> flux = Flux.just("failed "+e.getMessage());
			return flux;
		} catch (JsonProcessingException e) {
			Flux<Object> flux = Flux.just("failed "+e.getMessage());
			return flux;
		}

	}
}
