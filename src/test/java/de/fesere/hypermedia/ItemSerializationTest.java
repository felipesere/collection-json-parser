package de.fesere.hypermedia;

import junit.framework.Assert;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class ItemSerializationTest extends SerializationTestBase {

    private static final URI TEST_COM_ITEM = URI.create("http://test.com/item/1");

    @Test
    public void testPOJOtoJSON() {

        Item classUnderTest = new Item(TEST_COM_ITEM);

        assertSerialization("{\"href\":\"http://test.com/item/1\"}", classUnderTest);
    }

    @Test
    public void testJSONtoPOJO() {
        String givenJSON = "{\"href\":\"http://test.com/item/1\"}";

        Item item = deserialize(givenJSON, Item.class);

        Assert.assertEquals(TEST_COM_ITEM, item.getHref());
    }

    @Test
    public void testSerializeItemWithSingleDataEntry() {

        Item item = new Item(TEST_COM_ITEM);
        item.addData(new DataEntry("foo", "bar"));

        assertSerialization("{\"href\":\"http://test.com/item/1\", \"data\":[{\"name\":\"foo\", \"value\":\"bar\"}]}", item);
    }

    @Test
    public void testDeserializeItemWithSingleDataEntry() {

        String givenJSON = "{\"href\":\"http://test.com/item/1\", \"data\":[{\"name\":\"foo\", \"value\":\"bar\"}]}";

        Item item = deserialize(givenJSON, Item.class);

        Assert.assertEquals(TEST_COM_ITEM, item.getHref());
        assertThat(item.getData(), contains(data("foo", "bar")));
    }



    private Matcher<DataEntry> data(final String name, final String value) {
        return new TypeSafeMatcher<DataEntry>() {
            @Override
            public boolean matchesSafely(DataEntry dataEntry) {
               boolean nameMatches = name.equals(dataEntry.getName());
               if(value == null) {
                   return nameMatches && dataEntry.getValue() == null;
               }
               return nameMatches && dataEntry.getValue().equals(value);

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("a DataEntry with name="+name + " and value="+value);
            }
        };
    }


}
