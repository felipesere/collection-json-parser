package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.model.builder.CollectionBuilder;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import org.junit.Test;

import java.net.URI;

import static de.fesere.hypermedia.cj.model.builder.DataEntryFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class CollectionWithTypedItemsTest extends SerializationTestBase {

    @Test
    public void testWriteJsonWithMultipleTypes() {
        String json = readFile("/examples/read-collection-with-typed-data.json");
        CollectionBuilder builder = new CollectionBuilder(URI.create("http://example.org/friends/"));

        ItemBuilder itemBuilder = new ItemBuilder(URI.create("http://example.org/friends/jdoe"));
        itemBuilder.addData(create("full-name", "J. Doe", "Full Name"))
                   .addData(create("age", 24, "Persons Age"))
                   .addData(create("pi", 3.14159265359))
                   .addData(create("male", true))
                   .addData(createEmpty("empty", "some empty value"));
        Item item = itemBuilder.build();

        Collection collection = builder.addItem(item).build();

        assertSerialization(json, collection);
    }


   @Test
   public void testWriteJson_NonDataEntryIsNeverAddedToResultingJSON() {
       String json = readFile("/examples/read-collection-with-typed-data.json");
       CollectionBuilder builder = new CollectionBuilder(URI.create("http://example.org/friends/"));

       ItemBuilder itemBuilder = new ItemBuilder(URI.create("http://example.org/friends/jdoe"));
       itemBuilder.addData(create("full-name", "J. Doe", "Full Name"))
               .addData(create("age", 24, "Persons Age"))
               .addData(create("pi", 3.14159265359))
               .addData(create("male", true))
               .addData(createEmpty("empty", "some empty value"));


       DataEntry isNeverAddedToOutput = createNone();
       itemBuilder.addData(isNeverAddedToOutput);

       Item item = itemBuilder.build();

       Collection collection = builder.addItem(item).build();

       assertSerialization(json, collection);
   }




    @Test
    public void testReadJsonWithMultipleTypes() {

        Collection collection = readCollection("/examples/read-collection-with-typed-data.json");

        assertThat(collection.getItems(), hasSize(1));
        assertThat(collection.getItems().get(0).getString("full-name"), is("J. Doe"));
        assertThat(collection.getItems().get(0).getInt("age"), is(24));
        assertThat(collection.getItems().get(0).getDouble("pi"), is(closeTo(3.15, 0.1)));
        assertThat(collection.getItems().get(0).getBoolean("male"), is(true));
        assertThat(collection.getItems().get(0).getString("empty"), is(""));
    }


}
