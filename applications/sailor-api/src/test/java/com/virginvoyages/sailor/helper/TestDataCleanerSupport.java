package com.virginvoyages.sailor.helper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.client.AccountClient;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.QueryResultsData;

/**
 * Util class to remove test data
 * @author rpraveen
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDataCleanerSupport {
	
	@Autowired
	private QueryClient queryClient;
	
	@Autowired
	private AccountClient accountClient;
		
	@Autowired
	private SailorMapper sailorMapper;
	
	//TODO remove test annotation and plug in into test execution - before tests
	@Test
	public void cleanData() {
		//String[] listToDelete = new String[] {"Sapi_TestFN","MatchFN","FN_Changed1"};
		String[] listToDelete = new String[] {};
		for (String toDelete : listToDelete) {
			
			String selectQuery = "SELECT Id FROM Account WHERE FirstName LIKE '%"+toDelete+"%'";
			QueryResultsData<AccountData> queryResultsData = queryClient.findAccounts(selectQuery);
			
			List<String> listOfSailorIDs = sailorMapper.retrieveListOfSailorIDs(queryResultsData);
			//System.out.println("Request to return list of sailor's with ID {}"+listOfSailorIDs);
			
			for (String sailorID : listOfSailorIDs) {
				accountClient.deleteAccount(sailorID);
			}
			
		}
	}
}
