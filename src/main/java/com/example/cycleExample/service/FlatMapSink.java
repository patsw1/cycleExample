package com.example.cycleExample.service;

import java.time.Duration;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class FlatMapSink {

	Logger logger = LogManager.getLogger();

	void flatMap(FluxSink<Object> emitter) {
		Duration delay = Duration.ofSeconds(0);
		Duration period = Duration.ofSeconds(1);
		Flux<Long> intervalFlux = Flux.interval(delay, period).takeUntil((c) -> (c++ >= 5));

		intervalFlux = intervalFlux.doOnSubscribe((s) -> logger.info("onSubscribe interval"));
		intervalFlux = intervalFlux.doOnNext((s) -> logger.info("onNext interval "+s));
		intervalFlux = intervalFlux.doOnComplete(() -> {
			logger.info("onComplete interval ");
			emitter.complete();
		});
		
		//Function<Long,Flux<String>> x = (c)->Flux.just(c.toString());
		//Flux<String> mappedFlux = intervalFlux.flatMap(x);
		
		//Flux<String> mappedFlux = intervalFlux.flatMap((c)->Flux.just(c.toString()));
		//Flux<String> mappedFlux = intervalFlux.flatMap((c)->map(c));
		Flux<String> mappedFlux = intervalFlux.flatMap((c)->Flux.create(s->mapSink(s,c)));
		
		mappedFlux = mappedFlux.doOnSubscribe((s) -> logger.info("onSubscribe mapped"));
		mappedFlux = mappedFlux.doOnNext((s) -> logger.info("onNext mapped "+s));
		mappedFlux = mappedFlux.doOnComplete(() -> logger.info("onComplete mapped"));
		
		mappedFlux.subscribe();
		//intervalFlux.subscribe();
	}
	
	//Flux<String> map(Long c) {
	//	return Flux.just(c.toString());
	//}
	
	Flux<String> map(Long c) {
		Flux<String> flux = Flux.create(s->mapSink(s,c));
		return flux;
	}
	
	void mapSink(FluxSink<String> mapEmitter, Long c) {
		logger.info("mapSink "+c);
		mapEmitter.next("mapSink "+c);
		mapEmitter.complete();
	}
}
