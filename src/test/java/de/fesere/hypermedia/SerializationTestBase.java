package de.fesere.hypermedia;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.io.StringWriter;

public abstract class SerializationTestBase {

    public void assertSerialization(String expected, Object actual) {
        ObjectMapper mapper = new ObjectMapper();

        StringWriter sw = new StringWriter();

        try {
            mapper.writeValue(sw, actual);
        } catch (IOException e) {
            Assert.fail("Exception during serialization: " + e.getMessage());
        }

        System.out.println(sw);
        try {
            JSONAssert.assertEquals(expected, sw.toString(), false);
        } catch (JSONException e) {
            Assert.fail("Exception: " + e.getMessage());

        }
    }

    public <T> T deserialize(String givenJson, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(givenJson, clazz);
        } catch (IOException e) {
            Assert.fail("Failed to deserialize given Json: " + e.getMessage());
            return null; // fail(..) will throw an assertionError here
        }
    }
}
