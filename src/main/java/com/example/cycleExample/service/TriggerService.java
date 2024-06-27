package com.example.cycleExample.service;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class TriggerService {
	private Logger logger = LogManager.getLogger();

	public Flux<String> triggerWait() {
		logger.info("execting triggerWait");
		TriggerSink triggerSink = new TriggerSink();
		Flux<String> flux = Flux.create(s -> triggerSink.triggerWait(s));
		flux = flux.doOnError(e->logger.error(e));
		flux = flux.doOnNext(s->logger.info(s));
		return flux;
	}

	public Flux<String> triggerPull() {
		logger.info("execting triggerPull");
		TriggerSink triggerSink = new TriggerSink();
		Flux<String> flux = Flux.create(s -> triggerSink.triggerPull(s));
		flux = flux.doOnError(e->logger.error(e));
		flux = flux.doOnNext(s->logger.info(s));
		return flux;
	}

	public Flux<String> triggerFlux() {
		logger.info("execting triggerFlux");
		TriggerSink triggerSink = new TriggerSink();
		Flux<String> flux = Flux.create(s -> triggerSink.triggerFlux(s));
		flux = flux.doOnError(e->logger.error("service "+e));
		flux = flux.doOnNext(s->logger.info("service "+s));
		return flux;
	}
}

