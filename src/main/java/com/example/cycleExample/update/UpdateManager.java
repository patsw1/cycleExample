package com.example.cycleExample.update;

import java.util.concurrent.ConcurrentHashMap;

public class UpdateManager {
	static public UpdateManager getUpdateManager() {
		if (updateManager == null) {
			updateManager = new UpdateManager();
		}
		return updateManager;
	}

	static UpdateManager updateManager = null;

	private ConcurrentHashMap<String,UpdateRecord> map = new ConcurrentHashMap<>();
	public ConcurrentHashMap<String, UpdateRecord> getMap() {
		return map;
	}
	public void setMap(ConcurrentHashMap<String, UpdateRecord> map) {
		this.map = map;
	} 

}
