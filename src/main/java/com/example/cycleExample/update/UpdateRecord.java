package com.example.cycleExample.update;

import java.time.LocalDateTime;

public record UpdateRecord(String name, Long interval, Double randomFactor,
		LocalDateTime lastUpdate, Double value, Boolean running) {
}
