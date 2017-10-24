package com.virginvoyages.helper;

import java.util.UUID;

public class GUIDGenerator {
	
	public static String generateRandomGUID() {
		return UUID.randomUUID().toString();
	}

}
