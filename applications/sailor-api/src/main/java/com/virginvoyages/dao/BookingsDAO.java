package com.virginvoyages.dao;

import com.virginvoyages.crm.data.BookingData;
import com.virginvoyages.crm.data.QueryResultsData;

/**
 * {@code Interface} for dao tasks for sailor booking operation
 * @author pbovilla
 *
 */
public interface BookingsDAO {

	QueryResultsData<BookingData> getSailingHistory(String sailorID);
		
}
