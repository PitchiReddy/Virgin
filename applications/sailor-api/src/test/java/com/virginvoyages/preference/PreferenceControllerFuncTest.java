package com.virginvoyages.preference;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.FunctionalTestSupport;

@RunWith(SpringRunner.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })

public class PreferenceControllerFuncTest extends FunctionalTestSupport {
	
	//TODO Add Functional tests for Preference Controller.
	@Test
	public void validateSailorResponse() {
		//dummy
		assert(true);
	}

}
