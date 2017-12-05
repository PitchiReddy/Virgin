package com.virginvoyages.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableResourceServer
@ConditionalOnProperty(name = "api.security.enabled", havingValue = "true")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	TokenStore tokenStore;

	@Autowired
	JwtAccessTokenConverter tokenConverter;

	@Override
    public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/sailors**", "/references**"
                		, "/sources**","/types**", "/sailors/**" ,
                		
                		"/calenderRecommendation", 
                		"/contentRecommendation",
                		"/hereNowRecommendation",
                		"/marketingRecommendation",
                		"/recommendationRequest",
                		"/recommendationResponse",
                		"/shipNewsRecommendation",
                		"/tribe**",
                		"/tribe/**",
                		"/tribes"
                		)
               .authenticated();
    }
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		log.info("Configuring ResourceServerSecurityConfigurer ");
		resources.tokenStore(tokenStore);
	}
}