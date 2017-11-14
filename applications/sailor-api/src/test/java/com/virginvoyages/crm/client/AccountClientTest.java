package com.virginvoyages.crm.client;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.data.AccountCreateStatus;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.sailor.Sailor;
import com.virginvoyages.sailor.helper.TestDataHelper;
import com.virginvoyages.seaware.data.OTAProfileReadRS;

import feign.FeignException;

@RunWith(SpringRunner.class)
@SpringBootTest

public class AccountClientTest {

	
	@Autowired
	private AccountClient accountClient;
	
	@Autowired
	private SeawareClient seawareClient;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	
	@Test
    public void createAccountCreatesNewAccount() {
    	
		AccountData accountData = testDataHelper.generateAccountDataToCreate();
    	AccountCreateStatus accountStatus = accountClient.createAccount(accountData);
        
    	assertThat(accountStatus, is(notNullValue()));
        assertThat(accountStatus.successStatus(), equalTo("true"));
        assertThat(accountStatus, is(notNullValue()));
        
        AccountData createdAccount = accountClient.findAccount(accountStatus.id());
        
        assertThat(createdAccount.firstName(),equalTo(accountData.firstName()));
        assertThat(createdAccount.lastName(),equalTo(accountData.lastName()));
        assertThat(createdAccount.primaryEmail(),equalTo(accountData.primaryEmail()));
        assertThat(createdAccount.dateofBirth(),equalTo(accountData.dateofBirth()));
        assertThat(createdAccount.mobileNumber(),equalTo(accountData.mobileNumber()));
        
        testDataHelper.deleteSailor(createdAccount.id());
    }

    @Test
    public void findAccountReturnsAccoundData() {
    	
    	Sailor testSailor = testDataHelper.createTestSailor();
    	       
        AccountData accountData = accountClient.findAccount(testSailor.id());
        assertThat(accountData, is(notNullValue()));
        assertThat(testSailor.id(), equalTo(accountData.id()));
        
        testDataHelper.deleteSailor(testSailor.id());
    }
    
    @Test
    public void deleteAccountDeletesAccoundData() {
    	
    	Sailor testSailor = testDataHelper.createTestSailor();
    	
    	accountClient.deleteAccount(testSailor.id());
    	
    	try {
    		accountClient.findAccount(testSailor.id());
    	}catch(FeignException fe) {
    		assertThat(fe.getMessage(),equalTo(HttpStatus.NOT_FOUND.value()));
    		return;
    	}
        assert(false);
    }
    
    @Test
    public void findSeawareData() throws Exception {
    	OTAProfileReadRS otaProfileReadRS  = seawareClient.findseawareData(testDataHelper.genarateSeawaredataToCreate());
    	System.out.println("Data is ::::" + otaProfileReadRS.getProfiles());
    }
  
}
