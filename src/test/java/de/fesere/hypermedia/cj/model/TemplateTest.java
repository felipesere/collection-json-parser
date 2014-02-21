package de.fesere.hypermedia.cj.model;


import de.fesere.hypermedia.cj.model.builder.DataEntryFactory;
import de.fesere.hypermedia.cj.model.data.DataEntry;
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
        assertThat(result.getTemplate().getDataMap().keySet(), hasSize(4));
    }

    @Test
    public void test_fill() throws IOException {
        Template template = new Template(Arrays.asList(create("full-name", "", "Full Name"),
                create("age", ""),
                create("pi", ""),
                create("admin", "")));


        Item item = new Item(Arrays.asList(DataEntryFactory.create("full-name", "Felipe Sere"), create("age", 46), create("pi", 3.14), create("admin", false)));

        template.fill(item);

        assertTemplateSerialization("{ \"template\" : {\n" +
                "      \"data\" : [\n" +
                "        {\"name\" : \"full-name\", \"value\" : \"Felipe Sere\", \"prompt\" : \"Full Name\"},\n" +
                "        {\"name\" : \"age\", \"value\" : 46},\n" +
                "        {\"name\" : \"pi\", \"value\" : 3.14},\n" +
                "        {\"name\" : \"admin\", \"value\" : false}\n" +
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
