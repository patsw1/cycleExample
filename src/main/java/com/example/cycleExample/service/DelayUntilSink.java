package com.example.cycleExample.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class DelayUntilSink {

	private Logger logger = LogManager.getLogger();
	//
	//Flux<T> delayUntil(Function<? super T,? extends Publisher<?>> triggerProvider)
	//
	public void delayUntil(FluxSink<String> emitter) {
		logger.info("delayUntil started");
		emitter.next("delayUntil started");
		Flux<String> flux = Flux.just("a","b","c","d","e");
		Flux<String> delayFlux = Flux.create(s -> delayUntilSink(s, emitter));
		flux = flux.log();
		flux = flux.doOnNext(s -> {
			logger.info("onNext " + s);
			emitter.next("onNext " + s);
		});
		flux = flux.doOnComplete(() -> {
			logger.info("onComplete");
			emitter.next("onComplete");
		});
		flux.subscribe();
		logger.info("delayUntil completed");
		emitter.next("delayUntil completed");
		//emitter.complete();
	}

	private void delayUntilSink(FluxSink<String> delayUntilEmitter, FluxSink<String> emitter) {
		logger.info("delayUntilSink started");
		delayUntilEmitter.next("delayUntilSink started");
		emitter.next("delayUntilSink started");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		logger.info("delayUntilSink ended");
		delayUntilEmitter.next("delayUntilSink ended");
		emitter.next("delayUntilSink ended");
	}
}
