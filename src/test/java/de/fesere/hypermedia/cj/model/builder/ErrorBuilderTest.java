package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.SerializationTestBase;
import org.junit.Test;

import java.net.URI;

public class ErrorBuilderTest extends SerializationTestBase {

    @Test
    public void test() {
        String expectedJson = readFile("/examples/collection-with-error.json");
        URI base = URI.create("http://example.org/friends/");

        ErrorBuilder builder = new ErrorBuilder(base);
        builder.withTitle("Server Error");
        builder.withStatus("X1C2");
        builder.withMessage("The server have encountered an error, please wait and try again.");

        Collection collection = builder.build();

        assertCollectionSerialization(expectedJson, collection);

    }


}
