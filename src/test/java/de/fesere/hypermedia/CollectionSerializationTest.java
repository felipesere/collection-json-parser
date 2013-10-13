package de.fesere.hypermedia;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;

public class CollectionSerializationTest {

    @Test
    public void testPOJOtoJSON() {


        Collection collection = new Collection(URI.create("http://test.com"));

        assertSerialization("{\"collection\":{\"href\":\"http://test.com\",\"version\":\"1.0\"}}", collection );

    }


    @Test
    public void testJSONtoPOJO() {
        String givenJson = "{\"collection\":{\"href\":\"http://test.com\",\"version\":\"1.0\"}}";

        Collection collection = deserialize(givenJson);

        Assert.assertEquals("1.0", collection.getVersion());
        Assert.assertEquals(URI.create("http://test.com"), collection.getHref());

    }

    private Collection deserialize(String givenJson) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(givenJson, Collection.class);
        } catch (IOException e) {
            Assert.fail("Failed to deserialize given Json: " + e.getMessage());
            return null; // fail(..) will throw an assertionError here
        }
    }


    private void assertSerialization(String expected, Object actual) {
        ObjectMapper mapper = new ObjectMapper();

        StringWriter sw = new StringWriter();

        try {
            mapper.writeValue(sw, actual);
        } catch (IOException e) {
            Assert.fail("Exception during serialization: " + e.getMessage());
        }

        Assert.assertEquals(expected.trim(), sw.toString().trim());
        System.out.println(sw);
    }
}
