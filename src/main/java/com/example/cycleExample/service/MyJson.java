package com.example.cycleExample.service;

public class MyJson {

	static String get() {
		String json = """
				[{"name":"value"},
				{"map": {
				"name2" :"value2"}}]
				""";
		return json;
	}
}
