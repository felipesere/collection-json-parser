package de.fesere.hypermedia;

import junit.framework.Assert;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.readAllLines;
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

        Assert.assertEquals(URI.create("http://example.org/friends/"), result.getHref());
        Assert.assertEquals("1.0", result.getVersion());
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
                data("full-name", ""),
                data("email", ""),
                data("blog", ""),
                data("avatar", ""))
        );
    }

    @Test
    public void testCollectionWithFullExample() {
        String givenJson = readFile("examples/full-collection.json");

        Collection result = deserialize(givenJson, Collection.class);

        Assert.assertEquals(URI.create("http://example.org/friends/"), result.getHref());
        Assert.assertEquals("1.0", result.getVersion());

        assertThat("Incorrect number of links ", result.getLinks(), hasSize(3));
        assertThat("Incorrect number of items ", result.getItems(), hasSize(3));
        assertThat("Incorrect number of queries ", result.getQueries(),hasSize(1));
        assertThat("Temaplte not found ", result.getTemplate(), is(notNullValue()));

        assertThat(result.getTemplate().getData(), hasItems(
                data("full-name", ""),
                data("email", ""),
                data("blog", ""),
                data("avatar", ""))
        );
    }



    private String readFile(String filename) {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
           List<String> lines =  readAllLines(getDefault().getPath(path, filename), defaultCharset());

           return mergeLines(lines);
        } catch (IOException e) {
            Assert.fail(e.getMessage());

            return null;
        }

    }

    private String mergeLines(List<String> lines) {
        StringBuilder builder = new StringBuilder();
        for(String line : lines) {
            builder.append(line.trim());
        }

        return builder.toString();
    }

    private Matcher<DataEntry> data(final String name, final String value) {
        return new TypeSafeMatcher<DataEntry>() {
            @Override
            public boolean matchesSafely(DataEntry dataEntry) {
                boolean nameMatches = name.equals(dataEntry.getName());
                boolean valueMatches = value != null ? dataEntry.getValue().equals(value) : dataEntry.getValue() == null;

                return nameMatches && valueMatches;

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("a DataEntry with name="+name + " and value="+value);
            }
        };
    }
}
