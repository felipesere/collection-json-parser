package de.fesere.hypermedia.cj.transformer.annotations;

import de.fesere.hypermedia.cj.annotation.BasePerson;
import de.fesere.hypermedia.cj.annotation.NullPerson;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.builder.DataEntryFactory;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

public class FieldExtractorTest {

    @Test
    public void test_findingTheAnnotationReturnsList() {

        FieldExtractor extractor = new FieldExtractor();
        List<Field> result = extractor.annotatedFieldsFromType(BasePerson.class, Data.class);

        assertThat(result, hasSize(6));
    }

    @Test
    public void test_notFindingAnnotationReturnsEmptyList() {
        FieldExtractor extractor = new FieldExtractor();
        List<Field> result = extractor.annotatedFieldsFromType(BasePerson.class, ItemConfig.class);

        assertThat(result, hasSize(0));
    }


    @Test
    public void test_getAnnotatedConstructorOfType() {
        FieldExtractor extractor = new FieldExtractor();
        Constructor<BasePerson> constructor = extractor.findAnnotatedConstructor(BasePerson.class, Data.class);

        assertThat(constructor, is(notNullValue()));
    }


    @Test
    public void test() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        FieldExtractor extractor = new FieldExtractor();

        Item item = new ItemBuilder(URI.create("http://somewhere"))
                                .addData(DataEntryFactory.create("name", "Felipe"))
                                .addData(DataEntryFactory.create("age", 12))
                                .addData(DataEntryFactory.create("id", 1234))
                                .addData(DataEntryFactory.create("isAdmin", true))
                                .addData(DataEntryFactory.create("someValue", 1.23)).build();




        NullPerson p = extractor.createInstance(NullPerson.class, item.extractDataMap());
        assertThat(p, is(notNullValue()));
        assertThat(p.getAge(), is(12));
    }
}
