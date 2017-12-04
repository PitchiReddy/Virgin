package com.virginvoyages.seaware.dao.impl;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.seaware.client.SeawareClient;
import com.virginvoyages.seaware.data.OTAReadRQ;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeawareDAOImplTest {

	@Mock
    private SeawareClient seawareClient;
	
    @InjectMocks
    private SeawareDAOImpl seawareDAOImpl;
		
	@Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void givenSeawareClientThrowsExceptionGetSeawareClientDataShouldReturnNull() {
		when(seawareClient.findSeawareData(new OTAReadRQ())).thenThrow(new RuntimeException());
		assertThat(seawareDAOImpl.getSeawareClientData("123"), nullValue());
	}
	
	
}
