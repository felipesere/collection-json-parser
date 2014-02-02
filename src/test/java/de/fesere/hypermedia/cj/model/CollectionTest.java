package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.CollectionHasErrorsException;
import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.model.builder.CollectionBuilder;
import de.fesere.hypermedia.cj.model.builder.ErrorBuilder;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import de.fesere.hypermedia.cj.model.builder.LinkBuilder;
import de.fesere.hypermedia.cj.transformer.ReadTransformation;
import org.junit.Test;

import java.net.URI;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class CollectionTest {


    private URI baseURI;

    public CollectionTest(){
        baseURI = URI.create("http//foo/bar");
    }

    @Test(expected = CollectionHasErrorsException.class)
    public void test_exceptionWhenConvertingCollectionWithError() {
        Collection collection = new ErrorBuilder(baseURI).build();
        collection.transform(fooTransformer());
    }

    @Test
    public void test_convertSingleItem() {

        ItemBuilder itemBuilder = new ItemBuilder(baseURI);
        itemBuilder.addData(new DataEntry("foo", "Lorem ipsum"));

        Collection collection = new CollectionBuilder(baseURI).addItem(itemBuilder.build()).build();

        List<Foo> transform = collection.transform(fooTransformer());
        assertThat(transform, hasSize(1));
    }

    private ReadTransformation<Foo> fooTransformer() {
        return new ReadTransformation<Foo>() {
            @Override
            public Foo transform(Item item) {
                if(item == null) {
                    return null;
                }
                return new Foo(item.getString("foo"));
            }
        };
    }

    @Test(expected = ElementNotFoundException.class)
    public void test_getLinkWhichDoesNotExistThrowsException() {
        LinkBuilder builder = new LinkBuilder(baseURI);
        builder.addLink("bar","/batz");

        Collection collection = new Collection(baseURI, null, null, builder.build(), null);
        collection.getLink("doesNotExist");
    }

    @Test(expected = ElementNotFoundException.class)
    public void test_getQueryWhichDoesNotExistThrowsException() {

        Collection collection = new Collection(baseURI);
        collection.getQuery("doesNotExist");
    }

    private class Foo {
        public Foo(String something) {
        }
    }
}
