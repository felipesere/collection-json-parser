package de.fesere.hypermedia.cj.model;


import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.model.data.StringDataEntry;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static de.fesere.hypermedia.cj.model.builder.DataEntryFactory.create;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertNotNull;

public class TemplateTest extends SerializationTestBase {

    @Test
    public void testCollectionWithTemplate() {
        Collection result = readCollection("examples/template-collection.json");


        assertThat(result.getTemplate().getData(), hasSize(4));
        assertThat(result.getTemplate().getData(), hasItems(name("full-name"), name("email"), name("blog"), name("avatar")));
    }

    @Test
    public void test_fill() throws IOException {
        Template template = emptyTemplate();

        Item item = new Item(Arrays.<DataEntry>asList(new StringDataEntry("full-name", "Felipe Sere"), new StringDataEntry("email", "felipe@foo.com"), new StringDataEntry("blog", "Splash Of Color"), new StringDataEntry("avatar", "psycho.png")));

        template.fill(item);


        assertTemplateSerialization("{ \"template\" : {\n" +
                "      \"data\" : [\n" +
                "        {\"name\" : \"full-name\", \"value\" : \"Felipe Sere\", \"prompt\" : \"Full Name\"},\n" +
                "        {\"name\" : \"email\", \"value\" : \"felipe@foo.com\", \"prompt\" : \"Email\"},\n" +
                "        {\"name\" : \"blog\", \"value\" : \"Splash Of Color\", \"prompt\" : \"Blog\"},\n" +
                "        {\"name\" : \"avatar\", \"value\" : \"psycho.png\", \"prompt\" : \"Avatar\"}\n" +
                "      ]\n" +
                "    } }", template);
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

        Template template = serializer.deserialize(json, Template.class);
        assertNotNull(template);

    }

    private Template emptyTemplate() {
        List<DataEntry> entries = Arrays.asList(create("full-name", "", "Full Name"),
                                                create("email", "", "Email"),
                                                create("blog", "", "Blog"),
                                                create("avatar", "", "Avatar"));
        return new Template(entries);
    }
}
