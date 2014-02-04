package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import de.fesere.hypermedia.cj.model.builder.TemplateBuilder;
import de.fesere.hypermedia.cj.model.data.NumberDataEntry;
import de.fesere.hypermedia.cj.model.data.StringDataEntry;
import de.fesere.hypermedia.cj.transformer.ReadTransformation;
import org.junit.Test;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TemplateConverterTest {

    @Test
    public void test_convertingFromFilledTemplateToDomain() {
        Item item = new ItemBuilder(URI.create("http://foo.com")).addData(new StringDataEntry("name", "Felipe", "Users name")).addData(new NumberDataEntry("age", 24, "Users age")).build();
        Template template = new TemplateBuilder().filledFromItem(item).build();

        PersonConverter personConverter = new PersonConverter();
        Person felipe = template.convert(personConverter);

        assertThat(felipe.getName(), is("Felipe"));
        assertThat(felipe.getAge(), is(24));
    }

    private class PersonConverter implements ReadTransformation<Person> {
        @Override
        public Person transform(Item item) {
            return new Person(item.getString("name"), item.getInt("age"));
        }
    }

    private class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }
}
