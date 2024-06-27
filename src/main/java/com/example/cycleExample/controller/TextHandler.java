package com.example.cycleExample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cycleExample.service.TextService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TextHandler {
	private static Logger logger = LogManager.getLogger();

	public Mono<ServerResponse> text(ServerRequest serverRequest) {

		String mediaTypeString = "text/plain";
		
		if (serverRequest.queryParam("mediaType").isPresent()) {
			mediaTypeString = serverRequest.queryParam("mediaType").get();
		}
		try {
		MediaType mediaType = MediaType.valueOf(mediaTypeString);
		String s = mediaType.toString();
		logger.info("mediaType "+s);
		TextService textService = new TextService();
		Flux<String> flux = textService.text();
		Mono<ServerResponse> mono = ServerResponse.ok().contentType(mediaType).body(flux,Flux.class);
				return mono;
		} catch (Exception e) {
			String es = "mediaType "+e;
			logger.error(es);
			//BodyInserter<?, ? super ServerHttpResponse> z;
			//Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body("");
			Mono<ServerResponse> mono = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(es,String.class);
			return mono;
		}
	}
}
