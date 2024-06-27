package com.example.cycleExample.service;

import reactor.core.publisher.Flux;

public class ObjectService {
public Flux<Object> object() {
	ObjectSink objectSink = new ObjectSink();
	Flux<Object> flux = Flux.create(s->objectSink.object(s));
	return flux;
	
}
}
