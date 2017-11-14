package com.virginvoyages.crm.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.PreferenceData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.crm.data.RecordTypeData;

/**
* Interface for executing any query on CRM
* @author pbovilla
*
*/
@FeignClient(name = "queryclient", url = "${crm.service.url}/services/data/${crm.service.version}/query/", configuration = ClientConfiguration.class)
public interface QueryClient {

    String QUERY = "?q={query}";

    /**
      * Fetches account(s) based on input query 
      * @param query - Account query passing on given parameters like first name, last name etc.
      * @return QueryResultsData - Contains list of Account records return by the input query
     */
    @GetMapping(value = QUERY)
    QueryResultsData<AccountData> findAccounts(@RequestParam("query") String query);

    @GetMapping(value = "?q=SELECT Id from RecordType where Name = 'Sailor' LIMIT 1")
    QueryResultsData<RecordTypeData> findSailorAccountRecordTypeID();
    
    @GetMapping(value = QUERY)
    QueryResultsData<PreferenceData> getSailorPreferences(@RequestParam("query") String query);
    
}
