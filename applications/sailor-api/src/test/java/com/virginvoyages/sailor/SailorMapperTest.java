package com.virginvoyages.sailor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.data.AccountData;

/**
 * Test class for SailorMapper
 * @author rpraveen
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SailorMapperTest {
	
	@Test
	public void mapAccoundDataToSailorShouldMapAccountDataToSailorAttributes() {
		String firstName = "John";
		String lastName = "Mike";
		String salutation = "Mr.";

		AccountData accountData = new AccountData();
		accountData.firstName(firstName);
		accountData.lastName(lastName);
		accountData.salutation(salutation);
		Sailor sailor = SailorMapper.mapAccoundDataToSailor(accountData);
		
		assertThat(sailor.firstName(), is(firstName));
		assertThat(sailor.lastName(), is(lastName));
		assertThat(sailor.salutation(), is(salutation));
	}
	
	//TODO implement test
	/*@Test
	public void retrieveListOfSailorIDsShouldReturnAllSailorIDsInQueryResultsData() {
		assert(true);
	}*/
	
	
}
