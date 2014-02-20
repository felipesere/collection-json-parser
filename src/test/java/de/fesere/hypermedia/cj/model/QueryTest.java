package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.model.builder.DataEntryFactory;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import org.junit.Test;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

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

    @Test
    public void testBuildURI_singleElementInQueryIsSetToString() {

        Query theQuery = createBaseQueryWith3Entries();
        theQuery.set("foo", "a");

        URI uri = theQuery.buildURI();
        assertThat(uri.toString(), is(BASE_URI+"?foo=a"));
    }

    @Test
    public void testBuildURI_singleElementInQueryIsSetToInt() {

        Query theQuery = createBaseQueryWith3Entries();
        theQuery.set("foo", 12);

        URI uri = theQuery.buildURI();
        assertThat(uri.toString(), is(BASE_URI+"?foo=12"));
    }

    @Test
    public void testBuildURI_singleElementInQueryIsSetToBoolean() {

        Query theQuery = createBaseQueryWith3Entries();
        theQuery.set("foo", true);

        URI uri = theQuery.buildURI();
        assertThat(uri.toString(), is(BASE_URI+"?foo=true"));
    }

    @Test(expected = ElementNotFoundException.class)
    public void testBuildURI_settingNonExistingElementThrowsException() {

        Query theQuery = createBaseQueryWith3Entries();
        theQuery.set("doesNotExist", "a");
    }

    private Query createBaseQueryWith3Entries() {
        List<DataEntry> entries = new LinkedList<>();
        entries.add(DataEntryFactory.createEmpty("foo"));
        entries.add(DataEntryFactory.createEmpty("bar"));
        entries.add(DataEntryFactory.createEmpty("batz"));

        return new Query(URI.create(BASE_URI),
                "search","",  entries);
    }
}
