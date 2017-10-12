package com.virginvoyages.recommendations.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TestDataHelper {
	
	public Map<String,Object> getInputParamDataForContentRecommendations() {
		
		List<String> places = new ArrayList<String>();
		places.add("1|Spa");
		places.add("2|Shorex");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("requestSource", "VXP");
		parameters.put("sailorID", "123");
		parameters.put("channel", "WEB");
		parameters.put("place", places);
		
		return parameters;
	}
	
	public Map<String,String> getRecommendationResponseDataToSubmit() {
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("nbxUniqueKey", "890");
		parameters.put("sailorSelection", "991");
		parameters.put("selectionSentiment", "SELECTED");
	
		
		return parameters;
	}

	public Map<String, String> getTribeRequestParameters(){
		final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("requestSource", "TestSource");
        parameters.put("sailorId", "1234");
        
        return parameters;
	}
}
