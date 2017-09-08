package com.virginvoyages.recommendations.calendar.currentstate;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@Controller
@RequestMapping("/calendarRecommendation")
@Api(value="Calendar API", description="Calendar")
public class CalendarController {

	private HbaseRepo repository;

	@Autowired
	public CalendarController(HbaseRepo repository) {
		this.repository = repository;
	}

	//	@ApiOperation(value = "Recommendation Engine Data ",response = Iterable.class)
	//	@ApiResponses(value = {
	//			@ApiResponse(code = 200, message = "Successfully retrieved list"),
	//			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	//			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	//			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	//	}
	//			)
	//
	//	@RequestMapping(value = "/scan", method= RequestMethod.GET, produces = "application/json")
	//	public @ResponseBody void createTable() throws IOException {
	//		 repository.createTable();
	//	}


	@ApiOperation(value = "To request calendar recommendations from the recommendation engine.",response=CalendarBean.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
			)
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody List<CalendarBean> getSingleRow(
			@RequestParam(value = "reservationID", required = true) String[] reservationID,
//			@RequestParam(value = "requestSource", required = true) String requestSource,
			@RequestParam(value = "sailorID", required = true) String[] sailorID)
//			@RequestParam(value = "reservationID", required = true) String[] reservationID,
//			@RequestParam(value = "place", required = true) String[] place,
//			@RequestParam(value = "qtyPerPlace", required = true) String[] qtyPerPlace ,
//			@RequestParam(value = "dateRange", required = false) int dateRange )
//			@RequestParam(value = "channel", required = true) String channel,
//			@RequestParam(value = "metadata", required = false) String metadata)
					throws IOException {
		
		//return repository.getSingleRow(correlationID);
		return repository.getCalendarRecomendation(reservationID,sailorID);
	}



}
