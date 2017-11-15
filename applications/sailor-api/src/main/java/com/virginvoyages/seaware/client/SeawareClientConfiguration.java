package com.virginvoyages.seaware.client;

import org.springframework.context.annotation.Bean;

import feign.Contract;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBEncoder;

public class SeawareClientConfiguration {

	public Contract feignContract() {
		return new feign.Contract.Default();
	}

	/*@Bean
	public Client feignClient() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		Proxy proxyTest = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.2", 9999));
		okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().proxy(proxyTest)
				.connectTimeout(60, TimeUnit.SECONDS)
				 .readTimeout(60, TimeUnit.SECONDS)
				 .writeTimeout(60, TimeUnit.SECONDS)
				.addInterceptor(interceptor).build();
		return new OkHttpClient(client);
	}*/

	@Bean
	public Retryer retryer() {
		return new Retryer() {
			@Override
			public void continueOrPropagate(RetryableException e) {
				throw e;
			}
			@Override
			public Retryer clone() {
				return this;
			}
		};
	}

	@Bean
	public Encoder encoder() {
		JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder().withMarshallerJAXBEncoding("UTF-8")
				.withMarshallerSchemaLocation("http://www.opentravel.org/OTA/2003/05").build();
		return new JAXBEncoder(jaxbFactory);
	}

	@Bean
	public Decoder decoder() {
		return new SeawareJaxbDecoder();
	}
}