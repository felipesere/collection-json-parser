package de.fesere.hypermedia;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;

public class CollectionSerializationTest extends SerializationTestBase{

    public static final URI TEST_COM = URI.create("http://test.com");

    @Test
    public void testPOJOtoJSON() {


        Collection collection = new Collection(TEST_COM);

        assertSerialization("{\"collection\":{\"href\":\"http://test.com\",\"version\":\"1.0\"}}", collection );

    }


    @Test
    public void testJSONtoPOJO() {
        String givenJson = "{\"collection\":{\"href\":\"http://test.com\",\"version\":\"1.0\"}}";

        Collection collection = deserialize(givenJson, Collection.class);

        Assert.assertEquals("1.0", collection.getVersion());
        Assert.assertEquals(TEST_COM, collection.getHref());

    }

    @Test
      public void serializeCollectionWithSingleItem()     {
        Collection collection = new Collection(TEST_COM);

        collection.addItem(new Item(URI.create("http://test.com/items/1")));

        assertSerialization("{\"collection\":{\"href\":\"http://test.com\",\"version\":\"1.0\",\"items\":[{\"href\":\"http://test.com/items/1\"}]}}", collection);
    }

    @Test
    public void serializeCollectionWithSingleItemWithData()     {
        Collection collection = new Collection(TEST_COM);
        Item item = new Item(URI.create("http://test.com/items/1"));
        item.addData(new DataEntry("foo", "bar"));


        collection.addItem(item);

        assertSerialization("{\"collection\":{" +
                                "\"href\":\"http://test.com\"," +
                                "\"version\":\"1.0\"," +
                                "\"items\":[" +
                                    "{\"href\":\"http://test.com/items/1\"," +
                                     "\"data\":[" +
                                            "{\"name\":\"foo\", \"value\":\"bar\"}" +
                                        "]" +
                                     "}" +
                                  "]" +
                                "}" +
                              "}", collection);
    }



}
