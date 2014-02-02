package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.serialization.Serializer;
import de.fesere.hypermedia.cj.serialization.Wrapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.readAllLines;

public abstract class SerializationTestBase {

    private Serializer serializer;

    public SerializationTestBase() {
        serializer = new Serializer();
    }


    public void assertCollectionSerialization(String expected, Collection actual) {
        assertSerialization(expected, new Wrapper<>(actual));
    }

    public void assertSerialization(String expected, Object actual) {
        String actualJson = serializer.serialize(actual);

        System.out.println(actualJson);
        try {
            JSONAssert.assertEquals(expected, actualJson, false);
        } catch (JSONException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    public final String serialize(Object o){
        return serializer.serialize(o);
    }

    public final String serializeCollection(Collection c) {
        return serialize(new Wrapper<>(c));
    }


    public final <T> T deserialize(String givenJson, Class<T> clazz) {
       return  serializer.deserialize(givenJson, clazz);
    }

    public final Template deseriliazeTemplate(String givenJson) {
        return (Template) deserialize(givenJson, Wrapper.class).getElement();
    }

    public final Collection deserializeCollection(String giveJson) {
        return (Collection) deserialize(giveJson, Wrapper.class).getElement();
    }

    public final String readFile(String filename) {
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

    Matcher<DataEntry> name(final String name) {
        return new TypeSafeMatcher<DataEntry>() {
            @Override
            public boolean matchesSafely(DataEntry dataEntry) {
                return  name.equals(dataEntry.getName());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("a DataEntry with name="+name );
            }
        };
    }
}
