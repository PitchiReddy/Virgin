package com.virginvoyages.configuration;


import com.virginvoyages.api.LogFilter;
import com.virginvoyages.api.OriginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean originFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("origin-filter");
        registrationBean.setFilter(new OriginFilter());
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean logFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("log-filter");
        registrationBean.setFilter(new LogFilter());
        return registrationBean;
    }
}
