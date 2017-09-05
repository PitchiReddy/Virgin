package com.virginvoyages.assembly.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.sailor.Sailor;
import com.virginvoyages.sailor.exceptions.DataNotFoundException;

/**
 * @author rpraveen
 * Test Class for SailorAssembly Implementation
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SailorAssemblyTest {

	@Autowired
	private SailorAssembly sailorAssembly;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void givenSailorIDValidFindSailorbyIdShouldReturnSailor() {
		String sailorID = "0010n00000EPD14AAH";
		Sailor sailor = sailorAssembly.getSailorById(sailorID);
		assertThat("PreddyF12", equalTo(sailor.firstName()));
		assertThat("PreddyL12", equalTo(sailor.lastName()));
		assertThat("pb@test.com", equalTo(sailor.primaryEmail()));
	}

	@Test
	public void givenSailorIDInValidFindSailorbyIDShouldReturnPrettyError() {
		String sailorIDInvalid = "0010n00000EPD14AAHN";
		exception.expect(DataNotFoundException.class);
		sailorAssembly.getSailorById(sailorIDInvalid);
	}

	@Test
	public void givenSailorIDValidDeleteSailorbyIDShouldDeleteUser() {
		AccountData accountData = new AccountData();
		accountData.firstName("PreddyF12");
		accountData.lastName("PreddyL12");
		accountData.primaryEmail("pb@test.com");
		String dateofbirth = "2017-08-16";
		LocalDate localDate = LocalDate.parse(dateofbirth);
		accountData.dateofBirth(localDate);
		accountData.mobileNumber("1234567890");

		Sailor createSailor = sailorAssembly.createSailor(accountData);
		Sailor findSailor = sailorAssembly.getSailorById(createSailor.id());
		sailorAssembly.deleteSailorById(findSailor.id());
	}

	@Test
	public void givenSailorIDInValidDeleteSailorbyIDShouldReturnPrettyError() {
		String sailorID = "12345";
		try {
			sailorAssembly.deleteSailorById(sailorID);
		}catch(Exception ex) {
			assertThat(ex,instanceOf(DataNotFoundException.class));
		}
		
	}

	@Test
	public void givenUserExistsFindSailorsWithFirstNameReturnsAllUsersWithFirstnameMatching() {
		AccountData accountData = new AccountData();
		accountData.firstName("PreddyF1234");
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.firstName(), equalTo(accountData.firstName()));
		}
	}

	@Test
	public void givenUserNotExistsWithFirstnameMatchingFindReturnsEmptyList() {
		AccountData accountData = new AccountData();
		accountData.firstName("PreddyF16");
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		assertThat(listOfSailors, hasSize(0));
	}

	@Test
	public void givenUserExistsFindReturnsAllUsersWithFirstnameAndLastNameMatching() {
		AccountData accountData = new AccountData();
		accountData.firstName("PreddyF12");
		accountData.lastName("PreddyL13");
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		assertThat(listOfSailors, hasSize(1));
	}

	@Test
	public void givenUserNotExistsWithFirstnameAndLastNameMatchingFindReturnsEmptyList() {
		AccountData accountData = new AccountData();
		accountData.firstName("PreddyF123");
		accountData.lastName("PreddyL123");
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		assertThat(listOfSailors, hasSize(0));
	}

	@Test
	public void givenUserNotExistsWithCreateSailorAndReturnSailor() {
		AccountData accountData = new AccountData();
		accountData.firstName("PreddyF1234");
		accountData.lastName("PreddyL1234");
		accountData.dateofBirth(LocalDate.now());
		accountData.primaryEmail("pb@test5.com");
		Sailor sailor = sailorAssembly.createSailor(accountData);
		assertThat("PreddyF1234", equalTo(sailor.firstName()));
		assertThat("PreddyL1234", equalTo(sailor.lastName()));
		assertThat(LocalDate.now(), equalTo(sailor.dateofBirth()));
		assertThat("pb@test5.com", equalTo(sailor.primaryEmail()));
	}

}
