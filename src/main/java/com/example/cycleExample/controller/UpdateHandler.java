package com.example.cycleExample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cycleExample.service.ProcessService;
import com.example.cycleExample.service.UpdateService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UpdateHandler {

	private static Logger logger = LogManager.getLogger();

	public Mono<ServerResponse> updateStart(ServerRequest serverRequest) {
		logger.info("executing /update/start");
		String name = null;
		if (serverRequest.queryParam("name").isPresent()) {
			name = serverRequest.queryParam("name").get();
		}
		if (name == null) {
			Mono<ServerResponse> mono = ServerResponse.ok().bodyValue("specify Name");
			return mono;
		}

		Long interval = null;
		if (serverRequest.queryParam("interval").isPresent()) {
			String in = serverRequest.queryParam("interval").get();
			logger.info("interval="+in);
			interval = Long.valueOf(in);
		}
		if (interval == null) {
			Mono<ServerResponse> mono = ServerResponse.ok().bodyValue("specify interval");
			return mono;
		}

		Double randomFactor = null;
		if (serverRequest.queryParam("randomFactor").isPresent()) {
			String ra = serverRequest.queryParam("randomFactor").get();
			randomFactor = Double.valueOf(ra);
		}
		if (randomFactor == null) {
			Mono<ServerResponse> mono = ServerResponse.ok().bodyValue("specify randomFactor");
			return mono;
		}

		UpdateService updateService = new UpdateService();
		Flux<String> flux = updateService.updateStart(name, interval, randomFactor);
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, Flux.class);
		return mono;
	}

	public Mono<ServerResponse> updateReport(ServerRequest serverRequest) {
		logger.info("executing /update/report");
		UpdateService updateService = new UpdateService();
		Flux<String> flux = updateService.updateReport();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, Flux.class);
		return mono;
	}

}
