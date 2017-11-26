package com.virginvoyages.logging;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogFilterConfig {

    @Bean
    public FilterRegistrationBean logFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("log-filter");
        registrationBean.setFilter(new LogFilter());
        return registrationBean;
    }
}
