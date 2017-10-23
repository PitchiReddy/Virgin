package com.virginvoyages.helper;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class RandomDataGenerator {
	
	private static final int DEFAULT_DATA_SIZE = 5;
	
	public String generateRandomAlphabeticString() {
		return generateRandomAlphabeticString(DEFAULT_DATA_SIZE);
	}
	
	public String generateRandomAlphabeticString(int numberOfCharacters) {
		return RandomStringUtils.randomAlphabetic(numberOfCharacters);
	}
	
	public String generateRandomAlphabeticString(String prefix) {
		return null != prefix ? prefix+"_"+generateRandomAlphabeticString(DEFAULT_DATA_SIZE):generateRandomAlphabeticString(DEFAULT_DATA_SIZE);
	}
		
	public String generateRandomAlphabeticString(String prefix, int numberOfCharacters) {
		return null != prefix ? prefix+"_"+generateRandomAlphabeticString(numberOfCharacters):generateRandomAlphabeticString(numberOfCharacters);
	}

	public String generateRandomAlphaNumericString() {
		return generateRandomAlphaNumericString(DEFAULT_DATA_SIZE);
	}
	
	public String generateRandomAlphaNumericString(int numberOfCharacters) {
		return RandomStringUtils.randomAlphanumeric(numberOfCharacters);
	}
	
	public String generateRandomAlphaNumericString(String prefix) {
		return null != prefix ? prefix+"_"+generateRandomAlphaNumericString(DEFAULT_DATA_SIZE):generateRandomAlphaNumericString(DEFAULT_DATA_SIZE);
	}
		
	public String generateRandomAlphaNumericString(String prefix, int numberOfCharacters) {
		return null != prefix ? prefix+"_"+generateRandomAlphaNumericString(numberOfCharacters):generateRandomAlphaNumericString(numberOfCharacters);
	}
	
}
