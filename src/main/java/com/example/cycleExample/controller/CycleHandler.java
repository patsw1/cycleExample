package com.example.cycleExample.controller;



import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cycleExample.service.CycleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CycleHandler {

	public Mono<ServerResponse> cycle(ServerRequest serverRequest) {
		CycleService cycleService = new CycleService();
		Flux<String> flux = cycleService.cycle();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux,Flux.class);
				return mono;
	}
}
