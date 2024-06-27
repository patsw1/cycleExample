package com.example.cycleExample.service;

import reactor.core.publisher.Flux;

public class UpdateService {
	
	public Flux<String> updateStart(String name, Long interval, Double randomFactor) {
		UpdateSink updateSink = new UpdateSink();
		Flux<String> flux = Flux.create(s->updateSink.updateStart(s,name,interval,randomFactor));
		return flux;
	}

	public Flux<String> updateReport() {
		UpdateSink updateSink = new UpdateSink();
		Flux<String> flux = Flux.create(s->updateSink.updateReport(s));
		return flux;
	}
}

