package com.example.cycleExample.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.FluxSink;

public class ProcessSink {
	private Logger logger = LogManager.getLogger();

	public void process(FluxSink<String> emitter) {
		logger.info("processSink");
		emitter.next("processSink");

		regex();
		
		List<String> list = new ArrayList<String>();
		list.add("dir");

		ProcessBuilder processBuilder = new ProcessBuilder(list);
		processBuilder.redirectErrorStream(true);
		// starting the process
		try {
			Process process = processBuilder.start();
			// for reading the output from stream
			InputStream inputStream = process.getInputStream();
			//reader(emitter, inputStream);
			bytes(emitter,inputStream);
		} catch (IOException e) {
			logger.error(e);
			emitter.next(e.getMessage());
		}
		emitter.next("done.");
		emitter.complete();
	}

	void bytes(FluxSink<String> emitter, InputStream inputStream) {
		try {
		byte[] bytes = inputStream.readAllBytes();
		String s = new String(bytes,StandardCharsets.UTF_8);
		logger.info(s.toString());
		emitter.next(s.toString());		
		} catch (IOException e) {
			logger.error(e);
			emitter.next(e.getMessage());
		}
	}
	
	void reader(FluxSink<String> emitter, InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String s = null;
		try {
			while ((s = bufferedReader.readLine()) != null) {
				logger.info(s.toString());
				emitter.next(s.toString());
			}
		} catch (IOException e) {
			logger.error(e);
			emitter.next(e.getMessage());
		}
	}

	void regex() {
		 String regex = "(.*)(ey.*?)\".*";
		 String contents = "ABC ey123 4\"56";
		 Pattern pattern = Pattern.compile(regex);
		 Matcher matcher = pattern.matcher(contents);
		 if (matcher.matches()) {
			 String group = matcher.group(2);
			 logger.info("match="+group);
		 } else {
			 logger.info("no match");
		 
		 }
	}
}
