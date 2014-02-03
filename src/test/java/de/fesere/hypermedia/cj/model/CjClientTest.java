package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.http.DummyHTTPClient;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import de.fesere.hypermedia.cj.model.builder.TemplateBuilder;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class CjClientTest extends SerializationTestBase {

    public static final String BASE_URL = "http://writeToStringExample.com";
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
        String json = serializer.serialize(collectionWithMaxPerson());
        httpClient.expectGetLinkWith(URI.create("http://root.url")).returnStringOnGet(json);

        Collection result = classUnderTest.readCollection(URI.create("http://root.url"));

        Assert.assertNotNull(result);
    }

    @Test
    public void parserCreatesQuery() {
        String json = serializer.serialize(collectionWithMaxPerson());

        httpClient.expectGetLinkWith(URI.create("http://writeToStringExample.com/search?name=Max")).returnStringOnGet(json);

        Query query = new Query(URI.create("http://writeToStringExample.com/search"),"foo","Lorem ipsum", Arrays.asList(new StringDataEntry("name", "Max")));
        Collection result = classUnderTest.follow(query);

        assertThat(result, is(notNullValue()));
        assertThat(result.getItems(), hasSize(1));
    }


    @Test
    public void testAddItemToCollection() {
        Template template = new TemplateBuilder().emptyWithNames("foo", "bar", "batz").build();

        ItemBuilder builder = new ItemBuilder(URI.create(""));
        builder.addData(new StringDataEntry("foo", "a"))
               .addData(new StringDataEntry("bar", "b"))
               .addData(new StringDataEntry("batz", "c"));

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

        URI collectionHref = URI.create(BASE_URL);
        Collection resultCollection = classUnderTest.addItem(collectionHref, template);

        Assert.assertNotNull(resultCollection);

    }

    @Test
    public void test_updateItemInCollection() {
        Template template = new TemplateBuilder().emptyWithNames("foo", "bar", "batz").build();

        ItemBuilder builder = new ItemBuilder(URI.create(""));
        builder.addData(new StringDataEntry("foo", "a"))
                .addData(new StringDataEntry("bar", "b"))
                .addData(new StringDataEntry("batz", "c"));
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
        Item item = new Item(URI.create("http://writeToStringExample.com/people/1"), null);

        item.addData(new StringDataEntry("name", "Max"));
        item.addData(new StringDataEntry("surname", "Musterman"));

        Query query = new Query(URI.create("http://writeToStringExample.com/search"), "search", "", Arrays.asList(new StringDataEntry("name"), new StringDataEntry("surname")));
        List<DataEntry> entries =  new LinkedList<>();

        List<StringDataEntry> stringDataEntries = Arrays.asList(new StringDataEntry("foo"), new StringDataEntry("bar"), new StringDataEntry("batz"));
        for(StringDataEntry string : stringDataEntries) {
            entries.add(string);
        }


        Template template = new Template(entries);


        return new Collection(URI.create(BASE_URL), Arrays.asList(item), Arrays.asList(query), null, template);
    }
}
