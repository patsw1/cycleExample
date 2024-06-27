package com.example.cycleExample.service;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import reactor.core.publisher.FluxSink;

public class ObjectSink {
	//private static Logger logger = LogManager.getLogger();

	public void object(FluxSink<Object> emitter) {
		String json = MyJson.get();
		emitter.next(json);
		emitter.next(json);
		emitter.next(json);
		
		emitter.complete();
	}

}
