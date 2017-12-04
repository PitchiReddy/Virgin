package com.virginvoyages.sailor.helper;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import feign.Client;
import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;
import feign.okhttp.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Oauth2TokenClientConfiguration {

	public Contract feignContract() {
		return new feign.Contract.Default();
	}
	@Value("${swagger.oauth2.clientId}")
    private String clientId;
	
	@Value("${swagger.oauth2.clientSecret}")
    private String clientSecret;
	
	@Bean
	@Profile("local")
	public Client feignClient() {
		final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		final Proxy proxyTest = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.2", 9999));
		okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().proxy(proxyTest)
				.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS).addInterceptor(interceptor).build();
		return new OkHttpClient(client);
	}

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		return new BasicAuthRequestInterceptor(clientId, clientSecret);
	}

}
