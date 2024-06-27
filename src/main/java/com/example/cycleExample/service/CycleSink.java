package com.example.cycleExample.service;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class CycleSink {
	private static Logger logger = LogManager.getLogger();
	private static int counter = 0;
	private AtomicBoolean stop = new AtomicBoolean();

	public void cycle(FluxSink<String> emitter) {
		emitter.next("next");
		stop.set(false);
		Duration delay = Duration.ofSeconds(4L);
		Flux<String> flux = Flux.create(s -> inner(s));
		flux = flux.repeat();
		flux = flux.delayElements(delay);
		flux = flux.takeUntil(s -> stop.get());
		flux = flux.doOnSubscribe(s -> logger.info("onSubscribe "+ counter));
		flux = flux.doOnNext(s -> logger.info("onNext " + s + " " + counter));
		flux = flux.doOnComplete(() -> logger.info("onComplete "+ counter));
		flux.subscribe();

		emitter.complete();
	}

	void inner(FluxSink<String> innerEmitter) {
		//logger.info("insideStart");
		//try {
		//	Thread.sleep(1000);
		//} catch (InterruptedException e) {
		//	logger.error(e);
		//}
		counter++;
		logger.info("inside " + counter);
		innerEmitter.next(Integer.valueOf(counter).toString());
		innerEmitter.complete();
		if (counter % 5 == 0) {
			stop.set(true);
		}
	}
}
