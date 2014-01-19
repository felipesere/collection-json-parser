package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import org.junit.Test;

import java.net.URI;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

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

        assertEquals(TEST_COM_ITEM, item.getHref());
    }

    @Test
    public void testJSONwithAllPropertiesToPOJO() {

        String givenJSON = "{\"href\":\"http://test.com/item/1\", \"data\":[{\"name\":\"foo\", \"value\":\"bar\", \"prompt\":\"foos prompt\"}]}";

        Item item = deserialize(givenJSON, Item.class);
        assertThat(item.getHref(), is(TEST_COM_ITEM));

        DataEntry dataEntry = item.getData().get(0);
        assertThat(dataEntry.getPrompt(), is("foos prompt"));
        assertThat(dataEntry.getName(), is("foo"));
        assertThat(dataEntry.getValue(), is("bar"));


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

        assertEquals(TEST_COM_ITEM, item.getHref());
        assertThat(item.getData(), contains(name("foo")));
    }

    @Test(expected = ElementNotFoundException.class)
    public void testDeserializeItemWithSingleDataEntryMissingString() {

        String givenJSON = "{\"href\":\"http://test.com/item/1\", \"data\":[{\"name\":\"age\", \"value\":\"24\"}]}";

        Item item = deserialize(givenJSON, Item.class);
        item.getString("doesNotExist");

    }

    @Test(expected = ElementNotFoundException.class)
    public void testDeserializeItemWithSingleDataEntryMissingInteger() {

        String givenJSON = "{\"href\":\"http://test.com/item/1\", \"data\":[{\"name\":\"age\", \"value\":\"24\"}]}";

        Item item = deserialize(givenJSON, Item.class);
        item.getInt("doesNotExist");

    }

    @Test
    public void testDeserializeItemWithSingleDataEntryContainingInt() {

        String givenJSON = "{\"href\":\"http://test.com/item/1\", \"data\":[{\"name\":\"age\", \"value\":24}]}";

        Item item = deserialize(givenJSON, Item.class);

        assertEquals(TEST_COM_ITEM, item.getHref());
        assertThat(item.getData(), contains(name("age")));
        assertThat(item.getInt("age"), is(24));
    }

    @Test(expected = ElementNotFoundException.class)
    public void testDeserializeItemWithSingleDataEntryContainingMalformedInt_exception() {

        String givenJSON = "{\"href\":\"http://test.com/item/1\", \"data\":[{\"name\":\"age\", \"value\": \"24.abc\"}]}";

        Item item = deserialize(givenJSON, Item.class);

        assertEquals(TEST_COM_ITEM, item.getHref());
        assertThat(item.getData(), contains(name("age")));

        item.getInt("age");
    }
}
