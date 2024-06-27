package com.example.cycleExample.service;

import com.example.cycleExample.update.UpdateStart;
import com.example.cycleExample.update.UpdateReport;

import reactor.core.publisher.FluxSink;

public class UpdateSink {

	public void updateStart(FluxSink<String> emitter,
			String name, Long interval, Double randomFactor) {
		UpdateStart updateStart = new UpdateStart(); 
		updateStart.updateStart(emitter, name, interval, randomFactor); 
	}

	public void updateReport(FluxSink<String> emitter) {
		UpdateReport updateReport = new UpdateReport(); 
		updateReport.updateReport(emitter); 
	}
}
