package com.virginvoyages.sailor;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.crm.client.AccountClient;
import com.virginvoyages.crm.data.AccountData;

@RunWith(SpringRunner.class)
@WebMvcTest(SailorController.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class SailorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    AccountClient accountClient;

    @Test
    public void sailorGetShouldReturnExpectedAgeGroup() throws Exception {
        // Mock Setup
        String sailorID = "453";
        String ageGroup = "Senior";
        given(this.accountClient.findAccount(sailorID)).willReturn(new AccountData().id(sailorID).ageGroup(ageGroup));

        // Test
        mvc.perform(get("/sailors/" + sailorID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("ageGroup", is(ageGroup)));
    }
    
    @Test
    public void addSailorShouldCreateNewSailorInSORs() throws Exception {
    	//addSailor
    }
    
    @Test
    public void givenSailorIDValidGetSailorByIDShouldReturnSailor() throws Exception {
    	//getSailorById
    }
    
    @Test
    public void givenSailorIDInvalidGetSailorByIDShouldReturnErrorDetail() throws Exception {
    	//getSailorById
    }
    
    @Test
    public void givenSailorIDEmptyGetSailorByIDShouldReturnErrorDetail() throws Exception {
    	//getSailorById
    }
    
    // TODO Complete SailorController Unit tests.
    
    
}
