package com.virginvoyages.helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomDataGeneratorTest {
	
	@Autowired
	private RandomDataGenerator randomDataGenerator;
	
	@Test
	public void testGenerateRandomAlphabeticStringWithNoSizeSpecified() {
		String randomString = randomDataGenerator.generateRandomAlphabeticString();
		System.out.println("\n\n Random Alphabetic String ===>"+randomString+"\n\n");
		assertThat(randomString, notNullValue());
		assertThat(randomString.length(),equalTo(5));
		assertThat(StringUtils.isAlpha(randomString));
	}
	
	@Test
	public void testGenerateRandomAlphabeticStringWithSizeSpecified() {
		String randomString = randomDataGenerator.generateRandomAlphabeticString(10);
		System.out.println("\n\n Random Alphabetic String with size 10===>"+randomString+"\n\n");
		assertThat(randomString, notNullValue());
		assertThat(randomString.length(),equalTo(10));
		assertThat(StringUtils.isAlpha(randomString));
	}
	
	@Test
	public void testGenerateRandomAlphaNumericStringWithNoSizeSpecified() {
		String randomString = randomDataGenerator.generateRandomAlphaNumericString();
		System.out.println("\n\n Random Alphanumeric String ===>"+randomString+"\n\n");
		assertThat(randomString, notNullValue());
		assertThat(randomString.length(),equalTo(5));
		assertThat(StringUtils.isAlphanumeric(randomString));
	}
	
	@Test
	public void testGenerateRandomAlphaNumericStringWithSizeSpecified() {
		String randomString = randomDataGenerator.generateRandomAlphaNumericString(15);
		System.out.println("\n\n Random Alpha numeric String with size 15===>"+randomString+"\n\n");
		assertThat(randomString, notNullValue());
		assertThat(randomString.length(),equalTo(15));
		assertThat(StringUtils.isAlphanumeric(randomString));
		
	}
	
	
}
