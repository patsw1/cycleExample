package com.example.cycleExample.service;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionEvaluationListener;
import org.awaitility.core.ConditionTimeoutException;
import org.awaitility.core.EvaluatedCondition;
import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TriggerSink {
	private Logger logger = LogManager.getLogger();
	private AtomicBoolean lock = new AtomicBoolean(true);

	public void triggerWait(FluxSink<String> emitter) {
		logger.info("wait starts");
		emitter.next("wait starts");
		Duration duration = Duration.ofSeconds(10L);
		// Awaitility.await().timeout(duration).untilFalse(lock);
		try {
			TriggerEvaluation triggerEvaluation = new TriggerEvaluation();
			Awaitility.with().conditionEvaluationListener(condition -> {
				@SuppressWarnings("unchecked")
				EvaluatedCondition<AtomicBoolean> condition2 = condition;
				triggerEvaluation.conditionEvaluated(condition2);
			}).await().atMost(duration).untilFalse(lock);

		} catch (ConditionTimeoutException e) {
			logger.error("timeout: " + e);
		}
		logger.info("wait completed");
		emitter.next("wait completed");
		emitter.complete();

	}

	public void triggerPull(FluxSink<String> emitter) {
		logger.info("pull starts");
		emitter.next("pull starts");
		lock.set(true);
		try {
			Thread.sleep(500);
			lock.set(false);
		} catch (InterruptedException e) {
			logger.error(e);
		}
		logger.info("pull completed");
		emitter.next("pull completed");
		emitter.complete();
	}

	class TriggerEvaluation implements ConditionEvaluationListener<AtomicBoolean> {
		@Override
		public void conditionEvaluated(EvaluatedCondition<AtomicBoolean> condition) {
			String s = String.format("%s (elapsed time %dms, remaining time %dms)\n", condition.getDescription(),
					condition.getElapsedTimeInMS(), condition.getRemainingTimeInMS());
			// logger.info(s);
		}

	}

	public void xxtriggerFlux(FluxSink<String> emitter) {
		emitter.next("emitter triggerFlux");
		logger.info("logger triggerFlux");
		Flux<String> sequence = Flux.just("a", "b", "c", "last");

		Function<String, Publisher<String>> map1 = t -> Flux.create(s -> duplicateSink(s, t));
		Function<String, Publisher<String>> map2 = t -> Flux.create(s -> postSink(s, t));

		// = s -> Flux.just(s.toUpperCase().split(""));
		// Flux<String> duplicateFlux = Flux.create(s->duplicateSink(s));
		Flux<String> interFlux = sequence.flatMap(map1);
		Flux<String> outFlux = interFlux.flatMap(map2);
		outFlux.subscribe(s -> sequenceOnNext(s, emitter));
		// sequence.subscribe(s -> sequenceOnNext(s, emitter));
		// Consumer<? super String> x;
		// sequence.subscribe(x);

	}

	private void sequenceOnNext(String s, FluxSink<String> emitter) {
		emitter.next("emitter sequenceOnNext " + s);
		logger.info("logger sequenceOnNext " + s);
		if (s.equals("last")) {
			emitter.complete();
		}
	}

	private void duplicateSink(FluxSink<String> duplicateEmitter, String s) {
		try {
			Thread.sleep(Duration.ofSeconds(3));
		} catch (InterruptedException e) {

		}
		duplicateEmitter.next(s + " " + s);
		duplicateEmitter.complete();
	}

	private void postSink(FluxSink<String> postEmitter, String s) {
		postEmitter.next(s + " x");
		postEmitter.complete();
	}

	public void triggerFlux(FluxSink<String> emitter) {
		logger.info("hooks example");
		
		Hooks.onErrorDropped(error -> {
			logger.info("Exception happened: " + error);
		});

		Mono<String> mono = Mono.fromCallable(() -> {
			logger.info("fromCallable");
			Thread.sleep(Duration.ofSeconds(10));
			logger.info("after sleep");
			return "Success";
		}).subscribeOn(Schedulers.boundedElastic()).timeout(Duration.ofMillis(50))
				.onErrorResume(throwable -> Mono.just("Timeout"))
				.doOnSuccess(result -> logger.info("Result: " + result));
		
		mono.subscribe();
	}
}
