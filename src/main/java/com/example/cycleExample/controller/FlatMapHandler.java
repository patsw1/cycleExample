package com.example.cycleExample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cycleExample.service.FlatMapService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FlatMapHandler {
	private static Logger logger = LogManager.getLogger();

	public Mono<ServerResponse> flatMap(ServerRequest serverRequest) {

		String mediaTypeString = "application/json";
		
		if (serverRequest.queryParam("mediaType").isPresent()) {
			mediaTypeString = serverRequest.queryParam("mediaType").get();
		}
		MediaType mediaType = MediaType.valueOf(mediaTypeString);
		String s = mediaType.toString();
		logger.info("mediaType "+s);
		
		FlatMapService flatMapService = new FlatMapService();
		Flux<Object> flux = flatMapService.flatMap();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(mediaType).body(flux,Flux.class);
				return mono;
	}
}
