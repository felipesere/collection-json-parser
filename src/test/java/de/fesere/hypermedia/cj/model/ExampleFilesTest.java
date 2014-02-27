package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.CollectionHasErrorsException;
import org.junit.Test;

import java.net.URI;

import static junit.framework.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ExampleFilesTest extends SerializationTestBase {

    @Test
    public void testExampleFile() {
        Collection result = readCollection("examples/minimal-collection.json");

        assertEquals(URI.create("http://example.org/friends/"), result.getHref());
        assertEquals("1.0", result.getVersion());
    }

    @Test
    public void testCollectionWithSingleItem() {
        Collection result = readCollection("examples/item-collection.json");

        assertThat(result.getLinks(), hasSize(3));
        assertThat(result.getItems(), hasSize(1));
        assertThat(result.getItems().get(0).getLinks(), hasSize(2));
    }

    @Test
    public void testCollectionWithFullExample() {
        Collection result = readCollection("examples/full-collection.json");

        assertEquals(URI.create("http://example.org/friends/"), result.getHref());
        assertEquals("1.0", result.getVersion());
        assertFalse(result.hasError());

        assertThat("Incorrect number of links ", result.getLinks(), hasSize(3));
        assertThat("Incorrect number of items ", result.getItems(), hasSize(3));
        assertThat("Incorrect number of queries ", result.getQueries(),hasSize(1));
        assertThat("Template not found ", result.getTemplate(), is(notNullValue()));


        assertThat(result.getLinks(), hasSize(3));
        assertNotNull(result.getLink("feed"));
        assertThat(result.getLink("feed").getHref(), is(URI.create("http://example.org/friends/rss")));

        assertThat(result.getQueries(), hasSize(1));
        assertThat(result.getQuery("search").getData(),hasSize(1) );

    }

    @Test
    public void testCollectionWithError() {
        Collection result = readCollection("examples/collection-with-error.json");

        assertTrue("no error was set", result.hasError());
        assertEquals("Server Error", result.getError().getTitle());
        assertEquals("X1C2", result.getError().getCode());
        assertFalse(result.getError().getMessage().isEmpty());
    }

    @Test(expected = CollectionHasErrorsException.class)
    public void testCollectionWithErrorThrowsExceptionOnOtherAccessors() {
        Collection result = readCollection("examples/collection-with-error.json");
        result.getLinks();
    }
}
