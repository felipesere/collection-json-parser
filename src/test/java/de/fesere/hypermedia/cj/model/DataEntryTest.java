package de.fesere.hypermedia.cj.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataEntryTest {

    @Test
    public void equals_onlyName() {
        DataEntry a = new DataEntry("a");
        DataEntry b = new DataEntry("b");

        assertTrue(a.equals(a));
        assertFalse(a.equals(b));
        assertFalse(a.equals("Foo"));

        DataEntry c = new DataEntry("a");
        assertTrue(a.equals(c));

        DataEntry d = new DataEntry("a", "foo");
        assertFalse(a.equals(d));

        DataEntry e = new DataEntry("a", "foo", "prooooompt!");
        assertFalse(a.equals(e));


    }
}
