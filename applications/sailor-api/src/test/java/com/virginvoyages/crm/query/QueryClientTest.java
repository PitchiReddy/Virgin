package com.virginvoyages.crm.query;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.PreferenceData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.crm.data.RecordTypeData;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryClientTest {

    @Autowired
    private QueryClient queryClient;

    /*@Test
    public void dynamicQueryForAccount() {
        QueryResultsData<AccountData> queryResults = queryClient.findAccounts("SELECT id, Primary_Contact_Email__c FROM Account where Primary_Contact_Email__c = '"+ email + "' LIMIT 1");
        assertQueryResults(email, queryResults);
    }*/

    @Test
    public void typedQueryForRecordType() {
        QueryResultsData<RecordTypeData> queryResults = queryClient.findPersonAccountRecordTypeID();
        assertQueryResultHasOneRecord(queryResults);
        assertThat(queryResults.first(), is(notNullValue()));
    }

    @Test
    public void dynamicQueryForRecordType() {
        QueryResultsData<RecordTypeData> queryResults = queryClient.findRecordTypes("SELECT id FROM RecordType WHERE developername = 'PersonAccount' LIMIT 1");
        assertQueryResultHasOneRecord(queryResults);
        assertThat(queryResults.first(), is(notNullValue()));
    }
    
    @Test
    public void dynamicQueryForSailorPreferences() {
    	String sailorID = "0010n000006MqjYAAS";
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
        //assertThat(queryResults.totalSize(), );
        assertThat(queryResults.records(), is(notNullValue()));
        //assertThat(queryResults.records().size(), equalTo(1));
        // TODO put correct matchers.
    }


}
