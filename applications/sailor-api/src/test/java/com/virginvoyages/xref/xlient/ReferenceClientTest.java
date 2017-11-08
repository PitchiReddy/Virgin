package com.virginvoyages.xref.xlient;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.model.crossreference.Reference;
import com.virginvoyages.xref.client.CrossReferenceClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceClientTest {
	
	@Autowired
	private CrossReferenceClient crossReferenceClient;
	
	@Test
	public void testFindBySource() {
		Reference inputForFind = new Reference();
		inputForFind.nativeSourceIDValue("1");
		List<Reference> references = crossReferenceClient.findBySource(inputForFind);
		System.out.println(references);
	}
}
