package com.virginvoyages.data.util;

import java.util.UUID;

public class GUIDGenerator {
	
	public static String generateRandomGUID() {
		return UUID.randomUUID().toString();
	}

}
