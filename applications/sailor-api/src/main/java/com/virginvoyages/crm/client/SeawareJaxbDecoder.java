package com.virginvoyages.crm.client;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;

public class SeawareJaxbDecoder implements Decoder {

	@Override
	public Object decode(Response response, Type type) throws IOException {
		if (response.status() == 404)
			return Util.emptyValueOf(type);
		if (response.body() == null)
			return null;
		if (!(type instanceof Class)) {
			throw new UnsupportedOperationException("JAXB only supports decoding raw types. Found " + type);
		}
		try {
			final JAXBContext jaxbContext = JAXBContext.newInstance((Class) type);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return jaxbUnmarshaller.unmarshal(new InputSource(response.body().asInputStream()));
		} catch (JAXBException e) {
			throw new DecodeException(e.toString(), e);
		} finally {
			if (response.body() != null) {
				response.body().close();
			}
		}
	}
}
