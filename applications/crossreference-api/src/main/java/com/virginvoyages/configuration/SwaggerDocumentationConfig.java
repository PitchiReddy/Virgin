package com.virginvoyages.configuration;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.virginvoyages.oauth2.config.SwaggerProperties;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ClientCredentialsGrant;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;

@Configuration
public class SwaggerDocumentationConfig {

	public static final String securitySchemaOAuth2 = "oauth2Scheme";
	
	@Autowired
	private SwaggerProperties swaggerProperties;
	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("CrossReference API").description(
				"CrossReference API provides light weight orchestration services around CrossReference Data between different SORs.")
				.license("").licenseUrl("").termsOfServiceUrl("").version("0.0.1").contact(new Contact("", "", ""))

				.build();
	}

	public Docket customImplementation() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).build()
				.directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class).apiInfo(apiInfo());

	}

	@Bean
	@ConditionalOnProperty(name = "api.security.enabled", havingValue = "false")
	public Docket unsecuredDocket() {
		return customImplementation();
	}

	@Bean
	@ConditionalOnProperty(name = "api.security.enabled", havingValue = "true")
	public Docket securedDocket() {

		return customImplementation().securitySchemes(Collections.singletonList(securitySchema()))
				.securityContexts(Collections.singletonList(securityContext())).pathMapping("/");
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
		authorizationScopes[0] = new AuthorizationScope("read", "read all");
		authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
		authorizationScopes[2] = new AuthorizationScope("write", "write all");

		return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
	}

	private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = newArrayList();
		authorizationScopeList.add(new AuthorizationScope("read", "read all"));
		authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
		authorizationScopeList.add(new AuthorizationScope("write", "access all"));

		List<GrantType> grantTypes = newArrayList();
		GrantType creGrant = new ClientCredentialsGrant(
				swaggerProperties.getUrl());

		grantTypes.add(creGrant);

		return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/user/**"))
				.build();
	}

	@Bean
	@ConditionalOnProperty(name = "api.security.enabled", havingValue = "true")
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration("", "", "", "", "", ApiKeyVehicle.HEADER, "", " ");
	}
}
