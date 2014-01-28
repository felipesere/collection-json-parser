package de.fesere.hypermedia.cj.model.serialization;

import org.junit.Test;

public class WrapperDeserializerTest {

    Serializer serializer = new Serializer();

    @Test(expected = RuntimeException.class)
    public void test_onlyCollectionOrTemplateCanBeDeseriliazedWithWrapper() {
        serializer.deserialize("{\"foo\": \"bar\"}", Wrapper.class);
    }
}
