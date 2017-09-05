package com.virginvoyages.crm.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virginvoyages.crm.data.AccountCreateStatus;
import com.virginvoyages.crm.data.AccountData;

/**
* Class to handle account operations.
* @author pbovilla
*
*/
@FeignClient(name = "accountclient", url = "${crm.service.url}/services/data/${crm.service.version}/sobjects", configuration = ClientConfiguration.class)
public interface AccountClient {

	/**
      * Find sailor accounts based on given sailor id 
      * @param sailorID - input sailor id 
      * @return AccountData - contains account details for given sailor id
     */
	@RequestMapping(value = "/Account/{sailorID}", method = RequestMethod.GET)
    AccountData findAccount(@PathVariable("sailorID") String sailorID);
    
	/**
     * Create sailor in CRM based on given account details
     * @param accountData - account details info like first name and last name etc. 
     * @return AccountCreateStatus - contains account creation status
    */
    @RequestMapping(value = "/Account", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json")
    AccountCreateStatus createAccount(AccountData accountData);
    
    /**
     * Delete sailor in CRM based on given sailor ID
     * @param sailorID - input sailor id. 
     * @return boolean - contains deleted sailorID in CRM
    */
    @RequestMapping(value = "/Account/{sailorID}", method = RequestMethod.DELETE)
    void deleteAccount(@PathVariable("sailorID") String sailorID);
}


