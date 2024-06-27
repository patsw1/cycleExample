package com.example.cycleExample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cycleExample.service.ProcessService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProcessHandler {

	private static Logger logger = LogManager.getLogger();

	public Mono<ServerResponse> process(ServerRequest serverRequest) {
		logger.info("executing /process");
		ProcessService processService = new ProcessService();
		Flux<String> flux = processService.process();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, Flux.class);
		return mono;
	}
}
