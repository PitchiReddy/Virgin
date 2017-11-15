package com.virginvoyages.crossreference.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.virginvoyages.JacksonEncoder;

import feign.codec.Encoder;

@Configuration
public class CrossreferenceClientConfiguration {
    
    @Bean
    public Encoder encoder(){
        return new JacksonEncoder();
    }
   
}
