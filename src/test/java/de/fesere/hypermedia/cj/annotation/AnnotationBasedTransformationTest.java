package de.fesere.hypermedia.cj.annotation;

import de.fesere.hypermedia.cj.annotations.AnnotationBasedTransformer;
import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.SerializationTestBase;
import de.fesere.hypermedia.cj.model.builder.CollectionBuilder;
import org.junit.Test;

import java.net.URI;

public class AnnotationBasedTransformationTest extends SerializationTestBase {

    @Test
    public void test() {
        String json = readFile("/examples/annotation-based-item.json");

        Person person = new Person("felipe", 24, 4711, true, 1.234);

        AnnotationBasedTransformer transformer = new AnnotationBasedTransformer();
        CollectionBuilder personCollectionBuilder = new CollectionBuilder<>(URI.create("http://foobar.de"), transformer);
        personCollectionBuilder.addObject(person);


        Collection collection = personCollectionBuilder.build();
        assertCollectionSerialization(json, collection);
    }
}
