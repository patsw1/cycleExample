package com.example.cycleExample.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;

public class ProcessService {
	private Logger logger = LogManager.getLogger();

	public Flux<String> process() {
		logger.info("execting ProcessFlux");
		ProcessSink processSink = new ProcessSink();
		Flux<String> flux = Flux.create(s -> processSink.process(s));
		flux = flux.doOnError(e -> logger.error("service " + e));
		flux = flux.doOnNext(s -> logger.info("service " + s));
		return flux;
	}
}
