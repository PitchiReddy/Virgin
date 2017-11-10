package com.virginvoyages.crm.client;

import feign.codec.Decoder;
import feign.jaxb.JAXBContextFactory;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SeawareJaxbDecoder implements Decoder{

	 private final JAXBContextFactory jaxbContextFactory;

	  public SeawareJaxbDecoder(JAXBContextFactory jaxbContextFactory) {
	    this.jaxbContextFactory = jaxbContextFactory;
	  }

	  @Override
	  public Object decode(Response response, Type type) throws IOException {
	    if (response.status() == 404) return Util.emptyValueOf(type);
	    if (response.body() == null) return null;
	    if (!(type instanceof Class)) {
	      throw new UnsupportedOperationException(
	          "JAXB only supports decoding raw types. Found " + type);
	    }


	    try {
	      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	      /* Explicitly control sax configuration to prevent XXE attacks */
	      saxParserFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
	      saxParserFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
	      saxParserFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
	      saxParserFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

	      
	      
	      Source source = new SAXSource(saxParserFactory.newSAXParser().getXMLReader(), new InputSource(response.body().asInputStream()));
	      
	      Unmarshaller unmarshaller = jaxbContextFactory.createUnmarshaller((Class) type);
	      return unmarshaller.unmarshal(source);
	    } catch (JAXBException e) {
	      throw new DecodeException(e.toString(), e);
	    } catch (ParserConfigurationException e) {
	      throw new DecodeException(e.toString(), e);
	    } catch (SAXException e) {
	      throw new DecodeException(e.toString(), e);
	    } finally {
	      if (response.body() != null) {
	        response.body().close();
	      }
	    }
	  }
	
}
