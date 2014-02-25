package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static de.fesere.hypermedia.cj.model.builder.DataEntryFactory.createEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class QueryTest extends SerializationTestBase{

    private URI BASE_URI = URI.create("http://base.com");

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
        assertThat(uri, is(equalTo(BASE_URI)));
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

    @Test
    public void testBuildUri_settingBooleanAndNumber_success() {
       Query theQury = new Query(BASE_URI, "foo", null, Arrays.asList(createEmpty("admin"), createEmpty("age")));

       theQury.set("admin", true);
       theQury.set("age", 12);

       URI result = theQury.buildURI();
       assertThat(result.toString(), is(equalTo(BASE_URI + "?admin=true&age=12")));
    }

    private Query createBaseQueryWith3Entries() {
        List<DataEntry> entries = new LinkedList<>();
        entries.add(createEmpty("foo"));
        entries.add(createEmpty("bar"));
        entries.add(createEmpty("batz"));

        return new Query(BASE_URI,
                "search","",  entries);
    }
}
