package com.example.cycleExample.service;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class DelayUntilService {

	private static Logger logger = LogManager.getLogger();

	public Flux<String> delayUntil() {
		DelayUntilSink delayUntilSink = new DelayUntilSink(); 
		Flux<String> flux = Flux.create(s -> delayUntilSink.delayUntil(s));
		return flux;

	}
}
