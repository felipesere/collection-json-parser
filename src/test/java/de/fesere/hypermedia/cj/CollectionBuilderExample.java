package de.fesere.hypermedia.cj;


import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.data.BooleanDataEntry;
import de.fesere.hypermedia.cj.model.data.NumberDataEntry;
import de.fesere.hypermedia.cj.model.data.StringDataEntry;
import de.fesere.hypermedia.cj.model.builder.CollectionBuilder;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import de.fesere.hypermedia.cj.serialization.Serializer;
import org.junit.Test;

import java.net.URI;

public class CollectionBuilderExample {

    @Test
    public void test() {
        CollectionBuilder collectionBuilder = new CollectionBuilder(URI.create("http://example.com"));
        collectionBuilder.getLinkBuilder().addLink("documentation","/documentation/v1")
                                .addLink("questions", URI.create("http://stackoverflow.com")).build();

        ItemBuilder itemBuilder = new ItemBuilder(URI.create("http;//example.com/item/1"));
        itemBuilder.addData(new StringDataEntry("name", "Bob", "Users first name"));
        itemBuilder.addData(new NumberDataEntry("age", 24, "Users age"));
        itemBuilder.addData(new NumberDataEntry("height", 0.00192, "Users height in km"));
        itemBuilder.addData(new BooleanDataEntry("payed", false, "User payed fee"));

        Collection collection = collectionBuilder.addItem(itemBuilder.build()).build();

        Serializer serializer = new Serializer();
        System.out.println(serializer.serialize(collection));
    }
}
