package de.fesere.hypermedia.cj.example;

import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.data.StringDataEntry;
import de.fesere.hypermedia.cj.model.Template;
import de.fesere.hypermedia.cj.model.builder.CollectionBuilder;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import de.fesere.hypermedia.cj.model.builder.TemplateBuilder;
import de.fesere.hypermedia.cj.serialization.Serializer;
import org.junit.Test;

import java.net.URI;

public class ExampleCode {

    URI  baseUri = URI.create("http://test.com");
    Serializer serializer = new Serializer();

    @Test
    public void writeToStringExample() {
        CollectionBuilder collectionBuilder = new CollectionBuilder(baseUri);

        ItemBuilder itemBuilder = new ItemBuilder(baseUri);
        itemBuilder.addData(new StringDataEntry("foo", "bar"));
        collectionBuilder.addItem(itemBuilder.build());

        collectionBuilder.getLinkBuilder().addLink("search", "/search").build();

        Template template  = new TemplateBuilder().emptyWithNames("mac", "win").build();

        collectionBuilder.addTemplate(template);
        Collection collection = collectionBuilder.build();

        System.out.println(serializer.serialize(collection));
    }
}
