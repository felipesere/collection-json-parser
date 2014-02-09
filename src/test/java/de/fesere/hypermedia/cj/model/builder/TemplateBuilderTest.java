package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.SerializationTestBase;
import de.fesere.hypermedia.cj.model.Template;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;

import static de.fesere.hypermedia.cj.model.builder.DataEntryFactory.create;

public class TemplateBuilderTest extends SerializationTestBase {

    @Test
    public void test_generatePrefilledTemplateFromItem() {
        String expectedJson = readFile("/examples/builder/filled-template.json");

        Item item = new Item(Arrays.asList(create("name", "foo", "Full Name"), create("city", "karlsruhe", "City")));

        Template template = new TemplateBuilder().filledFromItem(item).build();

        Collection collection = new CollectionBuilder(URI.create("http://foo")).addTemplate(template).build();

        assertCollectionSerialization(expectedJson, collection);

    }

    @Test
    public void test_generateEmptyTemplateFromItem() {
        String expectedJson = readFile("/examples/builder/empty-template.json");

        Item item = new ItemBuilder(URI.create("http://foo")).addData(create("name", "foo", "Full Name"))
                                                              .addData(create("city", "karlsruhe", "City")).build();
        Template template = new TemplateBuilder().emptyFromItem(item).build();

        Collection collection = new CollectionBuilder(URI.create("http://foo")).addTemplate(template).build();

        assertCollectionSerialization(expectedJson, collection);
    }

    @Test
    public void test_generateEmptyTemplateUsingNames() {
        String expectedJson = readFile("/examples/builder/empty-template.json");

        Template template = new TemplateBuilder().emptyWithNames("name", "city").build();

        Collection collection = new CollectionBuilder(URI.create("http://foo")).addTemplate(template).build();
        assertCollectionSerialization(expectedJson, collection);
    }
}
