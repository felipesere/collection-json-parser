package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.CollectionHasErrorsException;
import org.junit.Test;

import java.net.URI;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class ExampleFilesTest extends SerializationTestBase {

    @Test
    public void testExampleFile() {
        String exampleJson = readFile("examples/minimal-collection.json");


        Collection result = deserialize(exampleJson, Collection.class);

        assertEquals(URI.create("http://example.org/friends/"), result.getHref());
        assertEquals("1.0", result.getVersion());
    }

    @Test
    public void testCollectionWithSingleItem() {
        String givenJson = readFile("examples/item-collection.json");

        Collection result = deserialize(givenJson, Collection.class);

        assertThat(result.getLinks(), hasSize(3));
        assertThat(result.getItems(), hasSize(1));
        assertThat(result.getItems().get(0).getLinks(), hasSize(2));

    }

    @Test
    public void testCollectionWithOnlyQuery() {
        String givenJson = readFile("examples/query-collection.json");

        Collection result = deserialize(givenJson, Collection.class);

        assertThat(result.getQueries(), hasSize(1));
        assertThat(result.getQueries().get(0).getData(),hasSize(1) );
    }

    @Test
    public void testCollectionWithTemplate() {
        String givenJson = readFile("examples/template-collection.json");

        Collection result = deserialize(givenJson, Collection.class);

        assertThat(result.getTemplate().getData(), hasSize(4));
        assertThat(result.getTemplate().getData(), hasItems(
                name("full-name"),
                name("email"),
                name("blog"),
                name("avatar"))
        );
    }

    @Test
    public void testCollectionWithFullExample() {
        String givenJson = readFile("examples/full-collection.json");

        Collection result = deserialize(givenJson, Collection.class);

        assertEquals(URI.create("http://example.org/friends/"), result.getHref());
        assertEquals("1.0", result.getVersion());
        assertFalse(result.hasError());

        assertThat("Incorrect number of links ", result.getLinks(), hasSize(3));
        assertThat("Incorrect number of items ", result.getItems(), hasSize(3));
        assertThat("Incorrect number of queries ", result.getQueries(),hasSize(1));
        assertThat("Temaplte not found ", result.getTemplate(), is(notNullValue()));

        assertThat(result.getTemplate().getData(), hasItems(
                name("full-name"),
                name("email"),
                name("blog"),
                name("avatar"))
        );
    }

    @Test
    public void testCollectionWithError() {
        String givenJson = readFile("examples/collection-with-error.json");

        Collection result = deserialize(givenJson, Collection.class);

        assertTrue("no error was set", result.hasError());
        assertEquals("Server Error", result.getError().getTitle());
        assertEquals("X1C2", result.getError().getCode());
        assertFalse(result.getError().getMessage().isEmpty());
    }

    @Test(expected = CollectionHasErrorsException.class)
    public void testCollectionWithErrorThrowsExceptionOnOtherAccessors() {
        String givenJson = readFile("examples/collection-with-error.json");

        Collection result = deserialize(givenJson, Collection.class);

        result.getLinks();
    }
}
