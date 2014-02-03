package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.*;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.model.data.StringDataEntry;
import de.fesere.hypermedia.cj.transformer.WriteTransformer;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CollectionBuilderTest extends SerializationTestBase {

    URI href = URI.create("http://example.org/friends/");

    @Test(expected = IllegalArgumentException.class)
    public void test_nullURIthrowsException() {
        new CollectionBuilder(null);
    }

    @Test
    public void test_buildCollectionWithTemplate() {
        String expextedJSON = readFile("/examples/template-collection.json");

        List<DataEntry> entries = new LinkedList<>();
        List<StringDataEntry> strings = Arrays.asList(new StringDataEntry("full-name", "", "Full Name"),
                new StringDataEntry("email", "", "Email"),
                new StringDataEntry("blog", "", "Blog"),
                new StringDataEntry("avatar", "", "Avatar"));

        for(DataEntry entry : strings) {
            entries.add(entry);
        }


        Template template = new Template(entries);


        Collection collection = new CollectionBuilder(href).addTemplate(template).build();

        assertCollectionSerialization(expextedJSON, collection);
    }

    @Test
    public void test_buildCollectionWithSingleQuery() {
        String expectedJSON = readFile("/examples/query-collection.json");


        URI queryURI = URI.create("http://example.org/friends/search");
        Query query = new Query(queryURI, "search", "Search", Arrays.<DataEntry>asList(new StringDataEntry("search", "")));

        Collection collection = new CollectionBuilder(href).addQuery(query).build();

        assertCollectionSerialization(expectedJSON, collection);
    }

    @Test
    public void test_addQueryWithRelativeURL() {
        String expectedJSON = readFile("/examples/query-collection.json");

        Query query = new Query("search", "Search", Arrays.<DataEntry>asList(new StringDataEntry("search", "")));

        Collection collection = new CollectionBuilder(href).addQuery(query, "/search").build();

        assertCollectionSerialization(expectedJSON, collection);
    }


    @Test
    public void test_addLinks() {
        String expected = readFile("/examples/builder/only-links.json");
        URI feeds = URI.create("http://example.org/friends/rss");
        URI template = URI.create("http://example.org/friends/?template");
        URI queries = URI.create("http://example.org/friends/?queries");

        CollectionBuilder collectionBuilder = new CollectionBuilder(href);
        collectionBuilder.getLinkBuilder().addLink("feed", feeds)
                                          .addLink("template", template)
                                          .addLink("queries", queries).build();

        Collection collection = collectionBuilder.build();

        assertCollectionSerialization(expected, collection);
    }

    @Test
    public void test_addLinksWithRelativeURL() {
        String expected = readFile("/examples/builder/only-links.json");

        CollectionBuilder collectionBuilder = new CollectionBuilder(href);
        collectionBuilder.getLinkBuilder().addLink("feed", "/rss")
                                          .addLink("template", "/?template")
                                          .addLink("queries", "?queries");

        Collection collection = collectionBuilder.build();
        assertCollectionSerialization(expected, collection);
    }


    @Test
    public void test_addSingleItemWithoutLinks() {
        String expected = readFile("/examples/builder/single-item-wihtout-links.json");

        PersonTransformerWithoutLinks transformer = new PersonTransformerWithoutLinks(URI.create("http://example.org"));
        Person person = new Person("jdoe", "J. Doe", "jdoe@example.org");
        Collection collection = new CollectionBuilder<>(href, transformer).addObject(person).build();

        assertCollectionSerialization(expected, collection);
    }

    @Test
    public void test_addSingleItemWithLinks() {
        String expected = readFile("/examples/builder/single-item-with-links.json");

        PersonTransformerWithLinks transformer = new PersonTransformerWithLinks(URI.create("http://example.org"));
        Person person = new Person("jdoe", "J. Doe", "jdoe@example.org");
        Collection collection = new CollectionBuilder<>(href, transformer).addObject(person).build();

        assertCollectionSerialization(expected, collection);
    }


    private class Person {
        private String id;
        private final String fullName;
        private final String email;

        public Person(String id, String fullName, String email) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }
    }

    private class PersonTransformerWithLinks implements WriteTransformer<Person> {

        UriConstructor constructor;

        public PersonTransformerWithLinks(URI base) {
           constructor = new UriConstructor(base);
        }

        @Override
        public Item transform(Person input) {
            ItemBuilder builder = new ItemBuilder(constructor.buildAbsoluteHrefFromRelative("/friends/"+input.id));
            builder.addData(new StringDataEntry("full-name", input.fullName , "Full Name"))
                   .addData(new StringDataEntry("email", input.email, "Email"));

            LinkBuilder linkBuilder = builder.getLinkBuilder();
            linkBuilder.addLink("blog",   constructor.buildAbsoluteHrefFromRelative("/blogs/"+input.id), "Blog")
                   .addLink("avatar", constructor.buildAbsoluteHrefFromRelative("/images/" + input.id), "Avatar");

           return builder.build();
        }
    }

    private class PersonTransformerWithoutLinks implements WriteTransformer<Person> {
        UriConstructor constructor;

        public PersonTransformerWithoutLinks(URI base) {
            constructor = new UriConstructor(base);
        }

        @Override
        public Item transform(Person input) {
            ItemBuilder builder = new ItemBuilder(constructor.buildAbsoluteHrefFromRelative("/friends/"+input.id));
            builder.addData(new StringDataEntry("full-name", input.fullName , "Full Name"))
                    .addData(new StringDataEntry("email", input.email, "Email"));

            return builder.build();
        }
    }
}

