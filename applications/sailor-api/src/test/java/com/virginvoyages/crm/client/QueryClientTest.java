package com.virginvoyages.crm.client;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.PreferenceData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.crm.data.RecordTypeData;
import com.virginvoyages.sailor.Sailor;
import com.virginvoyages.sailor.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryClientTest {

	@Autowired
    private QueryClient queryClient;
    
    @Autowired
    private TestDataHelper testDataHelper;

    @Test
    public void dynamicQueryForAccounts() {
    	
    	Sailor sailor = testDataHelper.createTestSailor();
    	QueryResultsData<AccountData> queryResults = queryClient.findAccounts("SELECT id, Primary_Contact_Email__c FROM Account where Primary_Contact_Email__c = '"+ sailor.primaryEmail()+"'");
        assertQueryResults(sailor.primaryEmail(), queryResults);
        testDataHelper.deleteSailor(sailor.id());
    }

    @Test
    public void typedQueryForRecordType() {
        QueryResultsData<RecordTypeData> queryResults = queryClient.findSailorAccountRecordTypeID();
        assertQueryResultHasOneRecord(queryResults);
        assertThat(queryResults.first(), is(notNullValue()));
    }
    
    @Test
    public void dynamicQueryForSailorPreferences() {
    	String sailorID = testDataHelper.getSailorIDWithPreferences();
    	String queryString = "SELECT Id,Category__c,Preference_Options__c,Sailor_ID__c,Sub_Category__c FROM Sailor_Preference__c WHERE Sailor_ID__c = '"+sailorID+"'";
    	QueryResultsData<PreferenceData> queryResults = queryClient.getSailorPreferences(queryString);
        assertQueryResultHasMoreThanOneRecord(queryResults);
        assertThat(queryResults.first(), is(notNullValue()));
    }

    private void assertQueryResults(String email, QueryResultsData<AccountData> queryResults) {
        assertQueryResultHasOneRecord(queryResults);
        AccountData data = queryResults.first();
        assertThat(data.primaryEmail(), equalTo(email));
    }

    private void assertQueryResultHasOneRecord(QueryResultsData<?> queryResults) {
        assertThat(queryResults, is(notNullValue()));
        assertThat(queryResults.done(), equalTo(true));
        assertThat(queryResults.totalSize(), equalTo(1));
        assertThat(queryResults.records(), is(notNullValue()));
        assertThat(queryResults.records().size(), equalTo(1));
    }
    
    private void assertQueryResultHasMoreThanOneRecord(QueryResultsData<?> queryResults) {
        assertThat(queryResults, is(notNullValue()));
        assertThat(queryResults.done(), equalTo(true));
        assertThat(queryResults.records(), is(notNullValue()));
        assertThat(queryResults.records(), hasSize(greaterThan(1)));
        
    }


}
