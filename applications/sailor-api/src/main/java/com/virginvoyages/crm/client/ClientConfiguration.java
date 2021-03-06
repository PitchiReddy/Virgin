package com.virginvoyages.crm.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import com.virginvoyages.JacksonEncoder;
import com.virginvoyages.crm.SecretsUtility;

import feign.RequestInterceptor;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientConfiguration {

    @Value("${security.oauth2.crm.access-token-uri}")
    private String accessTokenUri;

    @Value("${security.oauth2.crm.client-id}")
    private String clientId;

    @Value("${security.oauth2.crm.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.crm.username}")
    private String username;

    @Value("${security.oauth2.crm.password}")
    private String password;

    private SecretsUtility secrets = new SecretsUtility();

  
    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor() {
      String decodedUsername = secrets.decode(username);
      ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails = new ResourceOwnerPasswordResourceDetails();
              resourceOwnerPasswordResourceDetails.setAccessTokenUri(accessTokenUri);
      resourceOwnerPasswordResourceDetails.setClientId(clientId);
      resourceOwnerPasswordResourceDetails.setClientSecret(clientSecret);
      resourceOwnerPasswordResourceDetails.setUsername(decodedUsername);
      resourceOwnerPasswordResourceDetails.setPassword(secrets.decode(password));
      resourceOwnerPasswordResourceDetails.setClientAuthenticationScheme(AuthenticationScheme.query);

      log.debug("CRM User Name {}", decodedUsername);
     return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceOwnerPasswordResourceDetails);
  }

    
    
    @Bean
    public Encoder encoder(){
        return new JacksonEncoder();
    }
   
}
