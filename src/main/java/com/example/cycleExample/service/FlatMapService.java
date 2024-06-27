package com.example.cycleExample.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

public class FlatMapService {
	private static Logger logger = LogManager.getLogger();

	public Flux<Object> flatMap() {
		FlatMapSink flatMapSink = new FlatMapSink();
		Flux<Object> flux = Flux.create(s->flatMapSink.flatMap(s));
		return flux;
			
	}
}
