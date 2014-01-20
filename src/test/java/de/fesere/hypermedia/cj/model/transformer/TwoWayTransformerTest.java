package de.fesere.hypermedia.cj.model.transformer;

import de.fesere.hypermedia.cj.model.Item;
import org.junit.Test;

//For sake of consistency
public class TwoWayTransformerTest {
    private TwoWayTransformer t =  new DummyTransformer();

    @Test(expected = IllegalStateException.class)
    public void testConvertToItem() throws Exception {
        t.convert("foo");
    }

    @Test(expected = IllegalStateException.class)
    public void testConvertFromItem() throws Exception {
        t.convert(new Item());
    }

    private class DummyTransformer extends TwoWayTransformer<String> {

    }

}
