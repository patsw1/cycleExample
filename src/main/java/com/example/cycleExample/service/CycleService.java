package com.example.cycleExample.service;

import reactor.core.publisher.Flux;

public class CycleService {
public Flux<String> cycle() {
	CycleSink cycleSink = new CycleSink();
	Flux<String> flux = Flux.create(s->cycleSink.cycle(s));
	return flux;
	
}
}
