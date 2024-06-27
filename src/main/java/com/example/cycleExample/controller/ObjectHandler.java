package com.example.cycleExample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cycleExample.service.ObjectService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ObjectHandler {
	private static Logger logger = LogManager.getLogger();

	public Mono<ServerResponse> object(ServerRequest serverRequest) {

		String mediaTypeString = "text/plain";
		
		if (serverRequest.queryParam("mediaType").isPresent()) {
			mediaTypeString = serverRequest.queryParam("mediaType").get();
		}
		MediaType mediaType = MediaType.valueOf(mediaTypeString);
		String s = mediaType.toString();
		logger.info("mediaType "+s);
		
		ObjectService objectService = new ObjectService();
		Flux<Object> flux = objectService.object();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(mediaType).body(flux,Flux.class);
				return mono;
	}
}
