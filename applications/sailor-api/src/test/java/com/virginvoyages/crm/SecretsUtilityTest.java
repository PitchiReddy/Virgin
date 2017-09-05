package com.virginvoyages.crm;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.fail;

public class SecretsUtilityTest {

    SecretsUtility secrets = new SecretsUtility();

    @Test
    public void encodeAndDecode() {
        String toEncode = "String";
        String encoded = secrets.encode(toEncode);
        assertThat(toEncode, is(not(equalTo(encoded))));
        String decoded = secrets.decode(encoded);
        assertThat(toEncode, is(equalTo(decoded)));
    }

    @Test
    public void whenNullStringIsPassedDoNotFail() {
        try {
            assertThat(secrets.decode(null), is(nullValue()));
        } catch (Exception e) {
            fail("Unexpected exception for null decode" + e.getMessage());
        }
    }

    @Test
    public void whenEmptyStringIsPassedDoNotFail() {
        try {
            assertThat(secrets.decode(""), is(nullValue()));
        } catch (Exception e) {
            fail("Unexpected exception for empty decode" + e.getMessage());
        }
    }
}
