package com.virginvoyages.data.repositories;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.helper.TestDataHelper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceRepositoryTest {

	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void testFindReferenceByMaster() {

	}

}
