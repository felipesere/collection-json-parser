package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import org.junit.Test;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class CollectionTest {

    @Test(expected = ElementNotFoundException.class)
    public void test_getLinkWhichDoesNotExistThrowsException() {
        List<Link> links = new LinkedList<>();
        links.add(new Link("bar", URI.create("http://foo/bar")));

        Collection collection = new Collection(URI.create("http://foo"), null, null, links, null);
        collection.getLink("doesNotExist");

    }

    @Test(expected = ElementNotFoundException.class)
    public void test_getQueryWhichDoesNotExistThrowsException() {

        Collection collection = new Collection(URI.create("http://foo"));
        collection.getQuery("doesNotExist");

    }
}
