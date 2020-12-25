package br.com.token.pool.core.utils;

import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public class TimeUtils {
	
	private TimeUtils() {}

	public static Instant getNow() {
		
		return Instant.now();
	}

	public static long getNowTimestamp() {
		
		return Instant.now().toEpochMilli();
	}
}
