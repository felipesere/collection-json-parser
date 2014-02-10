package de.fesere.hypermedia.cj.annotation;

import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.SerializationTestBase;
import de.fesere.hypermedia.cj.model.builder.CollectionBuilder;
import org.junit.Test;

import java.net.URI;

public class AnnotationBasedTransformationTest extends SerializationTestBase {

    @Test
    public void testConversionNullSerializedAsNull() {
        String json = readFile("/examples/annotations/item-with-null.json");

        NullPerson person = new NullPerson("felipe", 24, 4711, true, 1.234);

        Collection collection = getCollectionWithPerson(person);
        assertCollectionSerialization(json, collection);
    }

    @Test
    public void testConversionNullSerializedDefaultAsNull() {
        String json = readFile("/examples/annotations/item-with-null.json");

        DefaultPerson person = new DefaultPerson("felipe", 24, 4711, true, 1.234);

        Collection collection = getCollectionWithPerson(person);
        assertCollectionSerialization(json, collection);
    }

    @Test
    public void testConversionNullSerializedAsEmpty() {
        String json = readFile("/examples/annotations/item-with-empty.json");

        EmptyPerson person = new EmptyPerson("felipe", 24, 4711, true, 1.234);

        Collection collection = getCollectionWithPerson(person);
        assertCollectionSerialization(json, collection);
    }


    @Test
    public void testConversionNullSerializedAsIgnored() {
        String json = readFile("/examples/annotations/item-without-ignored.json");

        IgnorePerson person = new IgnorePerson("felipe", 24, 4711, true, 1.234);

        Collection collection = getCollectionWithPerson(person);
        assertCollectionSerialization(json, collection);
    }


    private Collection getCollectionWithPerson(BasePerson person) {
        CollectionBuilder personCollectionBuilder = new CollectionBuilder<>(URI.create("http://foobar.de"));
        personCollectionBuilder.addObject(person);

        return personCollectionBuilder.build();
    }
}
