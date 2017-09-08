package com.virginvoyages.recommendations.calendar.currentstate;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@Controller
@RequestMapping("/tribe")
@Api(value="Tribe API", description="Tribe")
public class TribeController {

	private HbaseRepo repository;

	@Autowired
	public TribeController(HbaseRepo repository) {
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


	@ApiOperation(value = "Search a customer with an ID",response=TableBean.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
			)
	@RequestMapping(method = RequestMethod.GET, value ="/{sailorID}")
	public @ResponseBody TableBean getSingleRow( @PathVariable String sailorID) throws IOException {
		return repository.getSingleRow(sailorID);
	}
	
	

}
