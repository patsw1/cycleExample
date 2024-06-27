package com.example.cycleExample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cycleExample.service.TimeoutTestService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TimeoutTestHandler {

	private static Logger logger = LogManager.getLogger();

	public Mono<ServerResponse> timeoutTest(ServerRequest serverRequest) {
		TimeoutTestService timeoutTestService = new TimeoutTestService();
		Flux<String> flux = timeoutTestService.timeoutTest();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(flux, Flux.class);
		return mono;
	}
}
