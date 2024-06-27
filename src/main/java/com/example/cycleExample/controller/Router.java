package com.example.cycleExample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Router {

	private static Logger logger = LogManager.getLogger();

	@Bean
	RouterFunction<ServerResponse> cycle(CycleHandler handler) {
		logger.info("defining /cycle");
		RequestPredicate requestPredicate = RequestPredicates.GET("/cycle");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::cycle);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> text(TextHandler handler) {
		logger.info("defining /text");
		RequestPredicate requestPredicate = RequestPredicates.GET("/text");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::text);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> object(ObjectHandler handler) {
		logger.info("defining /object");
		RequestPredicate requestPredicate = RequestPredicates.GET("/object");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::object);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> basic(BasicHandler handler) {
		logger.info("defining /basic");
		RequestPredicate requestPredicate = RequestPredicates.GET("/basic");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::object);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> timeoutTest(TimeoutTestHandler handler) {
		logger.info("defining /timeoutTest");
		RequestPredicate requestPredicate = RequestPredicates.GET("/timeoutTest");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::timeoutTest);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> flatMap(FlatMapHandler handler) {
		logger.info("defining /flatmap");
		RequestPredicate requestPredicate = RequestPredicates.GET("/flatmap");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::flatMap);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> delayUntil(DelayUntilHandler handler) {
		logger.info("defining /delayUntil");
		RequestPredicate requestPredicate = RequestPredicates.GET("/delayUntil");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::delayUntil);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> triggerWait(TriggerHandler handler) {
		logger.info("defining /triggerWait");
		RequestPredicate requestPredicate = RequestPredicates.GET("/triggerWait");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::triggerWait);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> triggerPull(TriggerHandler handler) {
		logger.info("defining /triggerPull");
		RequestPredicate requestPredicate = RequestPredicates.GET("/triggerPull");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::triggerPull);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> triggerFlux(TriggerHandler handler) {
		logger.info("defining /triggerFlux");
		RequestPredicate requestPredicate = RequestPredicates.GET("/triggerFlux");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::triggerFlux);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> processFlux(ProcessHandler handler) {
		logger.info("defining /process");
		RequestPredicate requestPredicate = RequestPredicates.GET("/process");
		RouterFunction<ServerResponse> route = RouterFunctions.route(requestPredicate, handler::process);
		return route;
	}

	@Bean
	RouterFunction<ServerResponse> updateFlux(UpdateHandler handler) {
		logger.info("defining /update");
		RouterFunction<ServerResponse> route = RouterFunctions
				.route(RequestPredicates.GET("/update/start"), handler::updateStart)
				.andRoute(RequestPredicates.GET("/update/report"), handler::updateReport);
		return route;
	}
}
