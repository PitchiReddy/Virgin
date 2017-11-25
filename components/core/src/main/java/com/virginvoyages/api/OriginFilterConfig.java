package com.virginvoyages.configuration;

import com.virginvoyages.api.OriginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OriginFilterConfig {

    @Bean
    public FilterRegistrationBean originFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("origin-filter");
        registrationBean.setFilter(new OriginFilter());
        return registrationBean;
    }

}
