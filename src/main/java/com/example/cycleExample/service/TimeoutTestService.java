package com.example.cycleExample.service;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class TimeoutTestService {

	private static Logger logger = LogManager.getLogger();

	public Flux<String> timeoutTest() {
		Flux<String> flux = Flux.create(s -> timeoutTestSink(s));
		flux = flux.timeout(Duration.ofSeconds(1));
		flux = flux.doOnError(e->logger.error(e));
		flux = flux.doOnNext(s->logger.info(s));
		return flux;

	}

	private void timeoutTestSink(FluxSink<String> emitter) {
		logger.info("running timeoutTest");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error("");
		}
		emitter.next("timeoutTest successful");
		emitter.complete();
	}
}
