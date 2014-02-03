package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.exceptions.MalformedDataValueException;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.model.data.StringDataEntry;
import org.junit.Test;

import java.net.URI;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

public class ItemSerializationTest extends SerializationTestBase {

    private static final URI TEST_COM_ITEM = URI.create("http://writeToStringExample.com/item/1");

    @Test
    public void testPOJOtoJSON() {
        Item classUnderTest = new Item(TEST_COM_ITEM);

        assertSerialization("{\"href\":\"http://writeToStringExample.com/item/1\"}", classUnderTest);
    }

    @Test
    public void testJSONtoPOJO() {
        String givenJSON = "{\"href\":\"http://writeToStringExample.com/item/1\"}";

        Item item = serializer.deserialize(givenJSON, Item.class);

        assertEquals(TEST_COM_ITEM, item.getHref());
    }

    @Test
    public void testJSONwithAllPropertiesToPOJO() {

        String givenJSON = "{\"href\":\"http://writeToStringExample.com/item/1\", \"data\":[{\"name\":\"foo\", \"value\":\"bar\", \"prompt\":\"foos prompt\"}]}";

        Item item = serializer.deserialize(givenJSON, Item.class);
        assertThat(item.getHref(), is(TEST_COM_ITEM));

        DataEntry dataEntry = item.getData().get(0);
        assertThat(dataEntry.getPrompt(), is("foos prompt"));
        assertThat(dataEntry.getName(), is("foo"));
        assertThat((String) dataEntry.getValue(), is("bar"));
    }

    @Test
    public void testSerializeItemWithSingleDataEntry() {
        Item item = new Item(TEST_COM_ITEM);
        item.addData(new StringDataEntry("foo", "bar"));

        assertSerialization("{\"href\":\"http://writeToStringExample.com/item/1\", \"data\":[{\"name\":\"foo\", \"value\":\"bar\"}]}", item);
    }

    @Test
    public void testDeserializeItemWithSingleDataEntry() {
        String givenJSON = "{\"href\":\"http://writeToStringExample.com/item/1\", \"data\":[{\"name\":\"foo\", \"value\":\"bar\"}]}";

        Item item = serializer.deserialize(givenJSON, Item.class);

        assertEquals(TEST_COM_ITEM, item.getHref());
        assertThat(item.getData(), contains(name("foo")));
    }

    @Test(expected = ElementNotFoundException.class)
    public void testDeserializeItemWithSingleDataEntryMissingString() {
        String givenJSON = "{\"href\":\"http://writeToStringExample.com/item/1\", \"data\":[{\"name\":\"age\", \"value\":\"24\"}]}";

        Item item = serializer.deserialize(givenJSON, Item.class);

        item.getString("doesNotExist");
    }

    @Test(expected = ElementNotFoundException.class)
    public void testDeserializeItemWithSingleDataEntryMissingInteger() {
        String givenJSON = "{\"href\":\"http://writeToStringExample.com/item/1\", \"data\":[{\"name\":\"age\", \"value\":\"24\"}]}";

        Item item = serializer.deserialize(givenJSON, Item.class);
        item.getInt("doesNotExist");
    }

    @Test
    public void testDeserializeItemWithSingleDataEntryContainingInt() {
        String givenJSON = "{\"href\":\"http://writeToStringExample.com/item/1\", \"data\":[{\"name\":\"age\", \"value\":24}]}";

        Item item = serializer.deserialize(givenJSON, Item.class);

        assertEquals(TEST_COM_ITEM, item.getHref());
        assertThat(item.getData(), contains(name("age")));
        assertThat(item.getInt("age"), is(24));
    }

    @Test(expected = MalformedDataValueException.class)
    public void testDeserializeItemWithSingleDataEntryContainingMalformedInt_exception() {
        String givenJSON = "{\"href\":\"http://writeToStringExample.com/item/1\", \"data\":[{\"name\":\"age\", \"value\": \"24.abc\"}]}";

        Item item = serializer.deserialize(givenJSON, Item.class);

        assertEquals(TEST_COM_ITEM, item.getHref());
        assertThat(item.getData(), contains(name("age")));

        item.getInt("age");
    }


    @Test
    public void testDeserializeItemWithSingleDataEntryContainingDouble() {
        String givenJSON = "{\"href\":\"http://writeToStringExample.com/item/1\", \"data\":[{\"name\":\"distance\", \"value\":24.004}]}";

        Item item = serializer.deserialize(givenJSON, Item.class);

        assertEquals(TEST_COM_ITEM, item.getHref());
        assertThat(item.getData(), contains(name("distance")));
        assertThat(item.getDouble("distance"), is(24.004D));
    }

    @Test(expected = MalformedDataValueException.class)
    public void testDeserializeItemWithSingleDataEntryContainingInvalidDouble() {
        String givenJSON = "{\"href\":\"http://writeToStringExample.com/item/1\", \"data\":[{\"name\":\"distance\", \"value\": \"24.004xyz\"}]}";

        Item item = serializer.deserialize(givenJSON, Item.class);

        assertEquals(TEST_COM_ITEM, item.getHref());
        assertThat(item.getData(), contains(name("distance")));
        assertThat(item.getDouble("distance"), is(24.004D));
    }
}
