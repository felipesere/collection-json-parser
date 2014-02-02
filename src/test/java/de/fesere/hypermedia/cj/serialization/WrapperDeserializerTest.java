package de.fesere.hypermedia.cj.serialization;

import de.fesere.hypermedia.cj.exceptions.SerializationException;
import org.junit.Test;

public class WrapperDeserializerTest {


    @Test(expected = SerializationException.class)
    public void test_onlyCollectionOrTemplateCanBeDeseriliazedWithWrapper() {
        Serializer serializer = new Serializer();
        serializer.deserialize("{\"foo\": \"bar\"}", Wrapper.class);
    }
}
