package com.virginvoyages.crm;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.client.AccountClient;
import com.virginvoyages.crm.data.AccountCreateStatus;
import com.virginvoyages.crm.data.AccountData;

import feign.FeignException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountClientTest {

	
	@Autowired
	private AccountClient accountClient;
	
	@Test
    public void createAccountCreatesNewAccount() {
    	
		AccountData accountData = generateAccountDataToCreate();
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
        
        accountClient.deleteAccount(createdAccount.id());
    
    }

    @Test
    public void findAccountReturnsAccoundData() {
    	AccountData accountData = generateAccountDataToCreate();
    	AccountCreateStatus accountStatus = accountClient.createAccount(accountData);
        
        AccountData data = accountClient.findAccount(accountStatus.id());
        assertThat(data, is(notNullValue()));
        assertThat(accountStatus.id(), equalTo(data.id()));
        
        accountClient.deleteAccount(accountStatus.id());
    }
    
    @Test
    public void deleteAccountDeletesAccoundData() {
    	AccountData accountData = generateAccountDataToCreate();
    	AccountCreateStatus accountStatus = accountClient.createAccount(accountData);
    	
    	accountClient.deleteAccount(accountStatus.id());
    	
    	try {
    		accountClient.findAccount(accountStatus.id());
    	}catch(FeignException fe) {
    		if (HttpStatus.NOT_FOUND.value() == fe.status()) {
				assert(true);
				return;
			}
    	}
        assert(false);
    }
    
    AccountData generateAccountDataToCreate() {
    	AccountData accountData = new AccountData();
    	accountData.firstName("AccountClientTestFN");
    	accountData.lastName("AccountClientTestLN");
    	accountData.primaryEmail("test8@test.com");
    	accountData.dateofBirth(LocalDate.now());
    	accountData.mobileNumber("1234567890");
    	return accountData;
    }
    
   
}
