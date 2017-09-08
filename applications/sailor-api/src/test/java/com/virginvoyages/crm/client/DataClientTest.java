package com.virginvoyages.crm.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.MetaData;
import com.virginvoyages.crm.client.DataClient;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataClientTest {

    @Autowired
    DataClient dataClient;

    @Test
    public void healthCheck() {
        MetaData data = dataClient.metaData();
        assertThat(data, is(notNullValue()));
        assertThat(data.sobjects(), containsString("sobjects"));
    }
}
