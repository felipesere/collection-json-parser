package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.http.DummyHTTPClient;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class CjClientTest extends SerializationTestBase {

    public static final String BASE_URL = "http://test.com";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private DummyHTTPClient httpClient = new DummyHTTPClient();
    private CjClient classUnderTest = new CjClient(httpClient);

    @Before
    public void setup() {
        httpClient.clear();
    }

    @Test
    public void readCollectionFromURI() {
        String json = serializeCollection(collectionWithMaxPerson());
        httpClient.expectGetLinkWith(URI.create("http://root.url")).returnStringOnGet(json);

        Collection result = classUnderTest.readCollection(URI.create("http://root.url"));

        Assert.assertNotNull(result);
    }

    @Test
    public void parserCreatesQuery() {
        String json = serializeCollection(collectionWithMaxPerson());

        httpClient.expectGetLinkWith(URI.create("http://test.com/search?name=Max")).returnStringOnGet(json);

        Query query = new Query(URI.create("http://test.com/search"),"foo","Lorem ipsum", Arrays.asList(new DataEntry("name", "Max")));
        Collection result = classUnderTest.follow(query);

        assertThat(result, is(notNullValue()));
        assertThat(result.getItems(), hasSize(1));
    }


    @Test
    public void testAddItemToCollection() {
        Collection collection = collectionWithMaxPerson();
        Template template = collection.getTemplate();

        ItemBuilder builder = new ItemBuilder(URI.create(""));
        builder.addData(new DataEntry("foo", "a"))
               .addData(new DataEntry("bar", "b"))
               .addData(new DataEntry("batz", "c"));

        Item content = builder.build();
        template.fill(content);


        httpClient.expect(POST).on(URI.create(BASE_URL)).withHeader("Content-Type", "application/vnd.collection+json").body(
                "{\"template\" : {\n" +
                        "      \"data\" : [\n" +
                        "        {\"name\" : \"foo\", \"value\" : \"a\"},\n" +
                        "        {\"name\" : \"bar\", \"value\" : \"b\"},\n" +
                        "        {\"name\" : \"batz\", \"value\" : \"c\"},\n" +
                        "      ]\n" +
                        "    }" +
                        "}"
        );
        httpClient.respondWithURI(URI.create(BASE_URL + "/1"));
        httpClient.expectGetLinkWith(URI.create(BASE_URL + "/1"));
        httpClient.returnStringOnGet(readFile("examples/full-collection.json"));

        Collection resultCollection = classUnderTest.addItem(collection.getHref(),template);

        Assert.assertNotNull(resultCollection);

    }

    @Test
    public void test_updateItemInCollection() {
        Collection collection = collectionWithMaxPerson();
        Template template = collection.getTemplate();

        ItemBuilder builder = new ItemBuilder(URI.create(""));
        builder.addData(new DataEntry("foo", "a"))
                .addData(new DataEntry("bar", "b"))
                .addData(new DataEntry("batz", "c"));

        Item content = builder.build();
        template.fill(content);

        httpClient.expect(PUT).on(URI.create(BASE_URL+ "/1")).withHeader("Content-Type", "application/vnd.collection+json").body("{\"template\" : {\n" +
                "      \"data\" : [\n" +
                "        {\"name\" : \"foo\", \"value\" : \"a\"},\n" +
                "        {\"name\" : \"bar\", \"value\" : \"b\"},\n" +
                "        {\"name\" : \"batz\", \"value\" : \"c\"},\n" +
                "      ]\n" +
                "    }" +
                "}");
        httpClient.returnStringOnPUT(readFile("examples/full-collection.json"));
        

        URI itemHref = URI.create(BASE_URL + "/1");
        Collection result = classUnderTest.updateItem(itemHref, template);
        Assert.assertNotNull(result);
    }

    private Collection collectionWithMaxPerson() {
        Item item = new Item(URI.create("http://test.com/people/1"), null);

        item.addData(new DataEntry("name", "Max"));
        item.addData(new DataEntry("surname", "Musterman"));

        Query query = new Query(URI.create("http://test.com/search"), "search", "", Arrays.asList(new DataEntry("name"), new DataEntry("surname")));
        Template template = new Template(Arrays.asList(new DataEntry("foo"), new DataEntry("bar"), new DataEntry("batz")));


        return new Collection(URI.create(BASE_URL), Arrays.asList(item), Arrays.asList(query), null, template);
    }
}
