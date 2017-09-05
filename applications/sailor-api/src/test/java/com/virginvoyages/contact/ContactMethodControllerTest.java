package com.virginvoyages.contact;

import com.virginvoyages.api.MockSailorAPI;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactMethodController.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class ContactMethodControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MockSailorAPI mock;

    @Test
    public void sailorGetShouldReturnExpectedAgeGroup() throws Exception {
        // Mock Setup
        String sailorID = "453";
        String email = "mg";
        given(this.mock.sailorsSailorIDContactMethodGet(sailorID, 0, 0))
                .willReturn(new ContactMethods().embedded(new ContactMethodsEmbedded().addContactMethod(
                        new ContactEmail().email(email))));

        // Test
        mvc.perform(get("/sailors/" + sailorID + "/contactMethod")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
               // .andExpect(jsonPath("ageGroup", is(email)));
    }
}
