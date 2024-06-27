package com.example.cycleExample.update;

import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.FluxSink;

public class UpdateReport {
	
	private Logger logger = LogManager.getLogger();
	
	public void updateReport(FluxSink<String> emitter) {
		UpdateManager updateManager = UpdateManager.getUpdateManager();
		ConcurrentHashMap<String,UpdateRecord> map = updateManager.getMap();
		ObjectMapper  objectMapper = new  ObjectMapper();
		try {
			String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
			logger.info("updateReport "+s);
			emitter.next("updateReport "+s);
			emitter.complete();
		} catch (JsonProcessingException e) {
			logger.error(e);
		}
	}
}
