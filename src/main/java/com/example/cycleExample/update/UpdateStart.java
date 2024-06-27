package com.example.cycleExample.update;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.LongStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class UpdateStart {

	Logger logger = LogManager.getLogger();

	public void updateStart(FluxSink<String> emitter, String name, Long interval, Double randomFactor) {
		UpdateManager updateManager = UpdateManager.getUpdateManager();
		ConcurrentHashMap<String, UpdateRecord> map = updateManager.getMap();
		UpdateRecord updateRecord = map.get(name);
		LocalDateTime now = LocalDateTime.now();
		Boolean running = false;
		Double value = 1.0;
		// if (updateRecord == null) {
		// updateRecord = new UpdateRecord(name, interval, randomFactor, now, value,
		// running);
		// }
		// if (!updateRecord.running()) {
		// need to start
		Flux<String> updateFlux = Flux.create(s -> createUpdateFlux(s, name, interval, randomFactor, now, value));
		updateFlux.subscribe(s -> logger.info("updateFlux->" + s));
		// }
	}

	String synchMap(Long arg) {
		String s = arg.toString();
		return s;
	}

	Flux<String> asynchMap(Long arg) {
		String s = arg.toString();

		Flux<String> flux = Flux.just(s);
		return flux;
	}

	Flux<String> delayedFlux(Flux<Long>sourceFlux, Long interval) {
		Duration duration = Duration.ofMillis(interval);
		Flux<String> fs = sourceFlux.flatMap(a->delayMap(a,duration));
		return fs;
	}
	
	Flux<String> delayMap(Long arg, Duration duration) {
		Flux<String> fs = Flux.just(arg.toString()).delayElements(duration);
		return fs;
	}

	private void createUpdateFlux(FluxSink<String> updateEmitter, String name, Long interval, Double randomFactor,
			LocalDateTime now, Double value) {
		int start = 0;
		int end = 10;
		logger.info("createUpdateFlux name=" + name);
		List<Long> range = LongStream.rangeClosed(start, end).boxed().toList();
		Flux<Long> iterableFlux = Flux.fromIterable(range);
		iterableFlux = iterableFlux.doOnSubscribe(s3 -> logger.info("iterableFlux onSubscribe " + s3));
		iterableFlux = iterableFlux.doOnNext(s4 -> logger.info("iterableFlux onNext " + s4));
		iterableFlux = iterableFlux.doOnComplete(() -> logger.info("iterableFlux onComplete"));
		iterableFlux = iterableFlux.doOnError(s4 -> logger.info("iterableFlux onError " + s4));

		Flux<String> stringFluxSynch = iterableFlux.map(a -> synchMap(a));
		stringFluxSynch = stringFluxSynch.doOnSubscribe(s3 -> logger.info("stringFluxSynch onSubscribe " + s3));
		stringFluxSynch = stringFluxSynch.doOnNext(s4 -> logger.info("stringFluxSynch onNext " + s4));
		stringFluxSynch = stringFluxSynch.doOnComplete(() -> logger.info("stringFluxSynch onComplete"));
		stringFluxSynch = stringFluxSynch.doOnError(s4 -> logger.info("stringFluxSynch onError " + s4));

		//Flux<String> stringFluxAsynch = iterableFlux.flatMap(a -> asynchMap(a));
		//stringFluxAsynch = stringFluxAsynch.doOnSubscribe(s3 -> logger.info("stringFluxAsynch onSubscribe " + s3));
		//stringFluxAsynch = stringFluxAsynch.doOnNext(s4 -> logger.info("stringFluxAsynch onNext " + s4));
		//stringFluxAsynch = stringFluxAsynch.doOnComplete(() -> logger.info("stringFluxAsynch onComplete"));
		//stringFluxAsynch = stringFluxAsynch.doOnError(s4 -> logger.info("stringFluxAsynch onError " + s4));

		Flux<String> delayedFlux = delayedFlux(iterableFlux,interval);
		delayedFlux = delayedFlux.doOnSubscribe(s3 -> logger.info("delayedFlux onSubscribe " + s3));
		delayedFlux = delayedFlux.doOnNext(s4 -> logger.info("delayedFlux onNext " + s4));
		delayedFlux = delayedFlux.doOnComplete(() -> logger.info("delayedFlux onComplete"));
		delayedFlux = delayedFlux.doOnError(s4 -> logger.info("delayedFlux onError " + s4));
		
		// iterableFlux.subscribe(s1 -> logger.info(s1));
		//stringFluxSynch.subscribe(s1 -> logger.info(s1));
		//stringFluxAsynch.subscribe(s1 -> logger.info(s1));
		delayedFlux.subscribe(s1 -> logger.info("delayedFlux subscribed "+s1));
	}
}
