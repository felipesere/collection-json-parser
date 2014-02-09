package de.fesere.hypermedia.cj;


import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.builder.CollectionBuilder;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import de.fesere.hypermedia.cj.serialization.Serializer;
import org.junit.Test;

import java.net.URI;

import static de.fesere.hypermedia.cj.model.builder.DataEntryFactory.create;

public class CollectionBuilderExample {

    @Test
    public void test() {
        CollectionBuilder collectionBuilder = new CollectionBuilder(URI.create("http://example.com"));
        collectionBuilder.getLinkBuilder().addLink("documentation","/documentation/v1")
                                .addLink("questions", URI.create("http://stackoverflow.com")).build();

        ItemBuilder itemBuilder = new ItemBuilder(URI.create("http;//example.com/item/1"));
        itemBuilder.addData(create("name", "Bob", "Users first name"));
        itemBuilder.addData(create("surname-name", "Craftsman"));
        itemBuilder.addData(create("age", 24, "Users age"));
        itemBuilder.addData(create("id", 123808023));
        itemBuilder.addData(create("quoteien", 0.0019234124));
        itemBuilder.addData(create("married", true));

        Collection collection = collectionBuilder.addItem(itemBuilder.build()).build();

        Serializer serializer = new Serializer();
        System.out.println(serializer.serialize(collection));
    }
}
