package de.fesere.hypermedia.cj.model.serialization;

import de.fesere.hypermedia.cj.model.Collection;
import org.junit.Test;

public class SerializerTest {

   Serializer serializer = new Serializer();

    @Test(expected = RuntimeException.class)
    public void testDeserialize_failsOnException() throws Exception {
        serializer.deserialize("{----broken----}", Collection.class);
    }
}
