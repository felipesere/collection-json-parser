package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class QueryTest extends SerializationTestBase{

    private String BASE_URI = "http://base.com";

    @Test
    public void testCollectionWithOnlyQuery() {
        Collection result = readCollection("examples/query-collection.json");

        assertThat(result.getQueries(), hasSize(1));
        assertThat(result.getQueries().get(0).getData(),hasSize(1) );
    }


    @Test
    public void testBuildURI_noQueryStringIfQueryRemainsEmpty() {
        Query classUnderTest = createBaseQueryWith3Entries();

        URI uri = classUnderTest.buildURI();
        assertThat(uri.toString(), is(BASE_URI));
    }

    private Query createBaseQueryWith3Entries() {
        return new Query(URI.create(BASE_URI),
                 "search","",  Arrays.asList(new DataEntry("foo"),
                                         new DataEntry("bar"),
                                         new DataEntry("batz")));
    }

    @Test
    public void testBuildURI_singleElementInQueryIsSet() {

        Query theQuery = createBaseQueryWith3Entries();
        theQuery.set("foo", "a");

        URI uri = theQuery.buildURI();
        assertThat(uri.toString(), is(BASE_URI+"?foo=a"));
    }

    @Test(expected = ElementNotFoundException.class)
    public void testBuildURI_settingNonExistingElementThrowsException() {

        Query theQuery = createBaseQueryWith3Entries();
        theQuery.set("doesNotExist", "a");
    }
}
