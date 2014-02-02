package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fesere.hypermedia.cj.model.Collection;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SerializerTest {

   Serializer serializer = new Serializer();

    @Test(expected = RuntimeException.class)
    public void testDeserialize_failsOnException() throws Exception {
        serializer.deserialize("{----broken----}", Collection.class);
    }


    @Test(expected = RuntimeException.class)
    public void testSerialize_failsOnException() throws JsonProcessingException {
        ObjectMapper fakeMapper = mock(ObjectMapper.class);
        when(fakeMapper.writeValueAsString(any())).thenThrow(new JsonMappingException("Exception!"));
        Serializer serializer = new Serializer(fakeMapper);

        serializer.serialize("Foo");
    }
}
