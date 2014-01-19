package de.fesere.hypermedia.cj.model;

import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.readAllLines;

public abstract class SerializationTestBase {

    void assertSerialization(String expected, Object actual) {
        ObjectMapper mapper = new ObjectMapper();

        StringWriter sw = new StringWriter();

        try {
            mapper.writeValue(sw, actual);
        } catch (IOException e) {
            Assert.fail("Exception during serialization: " + e.getMessage());
        }

        System.out.println(sw);
        try {
            JSONAssert.assertEquals(expected, sw.toString(), false);
        } catch (JSONException e) {
            Assert.fail("Exception: " + e.getMessage());

        }
    }

    public final <T> T deserialize(String givenJson, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(givenJson, clazz);
        } catch (IOException e) {
            Assert.fail("Failed to deserialize given Json: " + e.getMessage());
            return null; // fail(..) will throw an assertionError here
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
