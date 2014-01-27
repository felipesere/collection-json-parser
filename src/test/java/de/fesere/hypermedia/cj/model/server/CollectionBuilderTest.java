package de.fesere.hypermedia.cj.model.server;

import de.fesere.hypermedia.cj.model.*;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;

public class CollectionBuilderTest extends SerializationTestBase {

    URI href = URI.create("http://example.org/friends/");

    @Test(expected = IllegalArgumentException.class)
    public void test_nullURIthrowsException() {
        new CollectionBuilder(null);
    }

    @Test
    public void test_buildCollectionWithTemplate() {
        String expextedJSON = readFile("/examples/template-collection.json");

        Template template = new Template(Arrays.asList( new DataEntry("full-name", "", "Full Name"),
                                                        new DataEntry("email", "", "Email"),
                                                        new DataEntry("blog", "", "Blog"),
                                                        new DataEntry("avatar", "", "Avatar")));


        Collection collection = new CollectionBuilder(href).addTemplate(template).build();

        assertCollectionSerialization(expextedJSON, collection);
    }

    @Test
    public void test_buildCollectionWithSingleQuery() {
        String expectedJSON = readFile("/examples/query-collection.json");


        URI queryURI = URI.create("http://example.org/friends/search");
        Query query = new Query(queryURI, "search", "Search", Arrays.asList(new DataEntry("search", "")));

        Collection collection = new CollectionBuilder(href).addQuery(query).build();

        assertCollectionSerialization(expectedJSON, collection);
    }

    @Test
    public void test_addQueryWithRelativeURL() {
        String expectedJSON = readFile("/examples/query-collection.json");


        Query query = new Query("search", "Search", Arrays.asList(new DataEntry("search", "")));

        Collection collection = new CollectionBuilder(href).addQuery(query, "/search").build();

        assertCollectionSerialization(expectedJSON, collection);
    }
}
