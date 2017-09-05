package com.virginvoyages.crm.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
* Class to handle list of CRM records.
* @author pbovilla
*
*/
@Data
@Accessors(fluent = true, chain = true)
public class QueryResultsData<T> {
	
	@JsonProperty("totalSize")
	private Integer totalSize;
	
	@JsonProperty("done")
	private Boolean done;

	@JsonProperty("records")
	private List<T> records;

	public T first() {
	    T first = null;
	    if (records != null && !records.isEmpty()) {
	        first = records.get(0);
        }
        return first;
    }
}
