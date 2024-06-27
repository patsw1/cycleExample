package com.example.cycleExample.service;

import reactor.core.publisher.Flux;

public class TextService {
public Flux<String> text() {
	TextSink textSink = new TextSink();
	Flux<String> flux = Flux.create(s->textSink.text(s));
	return flux;
	
}
}
