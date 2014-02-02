package de.fesere.hypermedia.cj.model;


import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertNotNull;

public class TemplateTest extends SerializationTestBase {

    @Test
    public void testCollectionWithTemplate() {
        String givenJson = readFile("examples/template-collection.json");

        Collection result = deserializeCollection(givenJson);


        assertThat(result.getTemplate().getData(), hasSize(4));
        assertThat(result.getTemplate().getData(), hasItems(
                name("full-name"),
                name("email"),
                name("blog"),
                name("avatar"))
        );
    }

    @Test
    public void test_fill() throws IOException {
        Template template = emptyTemplate();

        Item item = new Item(Arrays.asList(new DataEntry("full-name", "Felipe Sere"),
                new DataEntry("email", "felipe@foo.com"),
                new DataEntry("blog", "Splash Of Color"),
                new DataEntry("avatar", "psycho.png")));

        template.fill(item);


        assertTemplateSerialization("{ \"template\" : {\n" +
                "      \"data\" : [\n" +
                "        {\"name\" : \"full-name\", \"value\" : \"Felipe Sere\", \"prompt\" : \"Full Name\"},\n" +
                "        {\"name\" : \"email\", \"value\" : \"felipe@foo.com\", \"prompt\" : \"Email\"},\n" +
                "        {\"name\" : \"blog\", \"value\" : \"Splash Of Color\", \"prompt\" : \"Blog\"},\n" +
                "        {\"name\" : \"avatar\", \"value\" : \"psycho.png\", \"prompt\" : \"Avatar\"}\n" +
                "      ]\n" +
                "    } }",template);
    }

    @Test
    public void test() {
        String json = "{ \"template\" : {\n" +
                "      \"data\" : [\n" +
                "        {\"name\" : \"full-name\", \"value\" : \"Felipe Sere\", \"prompt\" : \"Full Name\"},\n" +
                "        {\"name\" : \"email\", \"value\" : \"felipe@foo.com\", \"prompt\" : \"Email\"},\n" +
                "        {\"name\" : \"blog\", \"value\" : \"Splash Of Color\", \"prompt\" : \"Blog\"},\n" +
                "        {\"name\" : \"avatar\", \"value\" : \"psycho.png\", \"prompt\" : \"Avatar\"}\n" +
                "      ]\n" +
                "    } }";

        Template template = deseriliazeTemplate(json);
        assertNotNull(template);

    }

    private Template emptyTemplate() {
        return new Template(Arrays.asList(new DataEntry("full-name", "", "Full Name"),
                new DataEntry("email", "", "Email"),
                new DataEntry("blog", "", "Blog"),
                new DataEntry("avatar", "", "Avatar")));
    }
}