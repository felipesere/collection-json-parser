package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.serialization.Serializer;
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

    Serializer serializer;

    public SerializationTestBase() {
        serializer = new Serializer();
    }


    public void assertCollectionSerialization(String expected, Collection collection) {
        String actual =  serializer.serialize(collection);
        assertJson(expected, actual);
    }


    public void assertTemplateSerialization(String expected, Template template){
        String actual = serializer.serialize(template);
        assertJson(expected, actual);
    }

    public void assertSerialization(String expected, Object actual) {
        String actualJson = serializer.serialize(actual);

        assertJson(expected, actualJson);
    }

    private void assertJson(String expected, String actualJson) {
        System.out.println("Actual: \n" + actualJson);
        try {
            JSONAssert.assertEquals(expected, actualJson, false);
        } catch (JSONException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
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
    public final Collection readCollection(String filename) {
        String json = readFile(filename);

        return serializer.deserialize(json, Collection.class);
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
                description.appendText("a StringDataEntry with name="+name );
            }
        };
    }
}
