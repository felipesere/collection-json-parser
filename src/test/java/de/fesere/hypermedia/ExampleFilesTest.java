package de.fesere.hypermedia;

import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.readAllLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

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
}
