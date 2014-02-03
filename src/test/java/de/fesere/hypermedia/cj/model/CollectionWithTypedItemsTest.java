package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.model.builder.CollectionBuilder;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import org.junit.Test;

import java.net.URI;

public class CollectionWithTypedItemsTest extends SerializationTestBase {

    @Test
    public void test() {
        String json = readFile("/examples/collection-with-typed-data.json");
        CollectionBuilder builder = new CollectionBuilder(URI.create("http://example.org/friends/"));

        ItemBuilder itemBuilder = new ItemBuilder(URI.create("http://example.org/friends/jdoe"));
        itemBuilder.addData(new StringDataEntry("name", "J. Doe", "Full Name"))
                   .addData(new StringDataEntry("age", "24", "Persons Age"));
        Item item = itemBuilder.build();

        Collection collection = builder.addItem(item).build();

        assertSerialization(json, collection);
    }
}
