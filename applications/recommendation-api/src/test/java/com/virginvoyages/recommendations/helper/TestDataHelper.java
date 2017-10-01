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

}
