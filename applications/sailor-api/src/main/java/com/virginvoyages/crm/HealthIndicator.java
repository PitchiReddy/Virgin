package com.virginvoyages.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import com.virginvoyages.crm.client.DataClient;

@Component("crmHealth")
public class HealthIndicator extends AbstractHealthIndicator {

    @Autowired
    DataClient dataClient;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.status(Status.UP).withDetail("SFDC Object Metadata", dataClient.metaData().toString());
    }
}
