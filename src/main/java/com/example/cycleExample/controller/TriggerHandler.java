package com.example.cycleExample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cycleExample.service.TriggerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TriggerHandler {


	private static Logger logger = LogManager.getLogger();

	public Mono<ServerResponse> triggerWait(ServerRequest serverRequest) {
		TriggerService triggerService = new TriggerService();
		Flux<String> flux = triggerService.triggerWait();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, Flux.class);
		return mono;
	}

	public Mono<ServerResponse> triggerPull(ServerRequest serverRequest) {
		TriggerService triggerService = new TriggerService();
		Flux<String> flux = triggerService.triggerPull();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, Flux.class);
		return mono;
	}

	public Mono<ServerResponse> triggerFlux(ServerRequest serverRequest) {
		TriggerService triggerService = new TriggerService();
		Flux<String> flux = triggerService.triggerFlux();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, Flux.class);
		return mono;
	}

}
