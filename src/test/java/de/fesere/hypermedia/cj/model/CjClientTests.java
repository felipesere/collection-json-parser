package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.http.DummyHTTPClient;
import de.fesere.hypermedia.cj.model.transformer.ReadTransformation;
import junit.framework.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class CjClientTests extends SerializationTestBase {

    public static final String BASE_URL = "http://test.com";
    private static final String POST = "POST";

    @Test
    public void readCollectionFromURI() {
        Collection collection = collection();
        DummyHTTPClient httpClient = new DummyHTTPClient();
        httpClient.expectGetLinkWith(URI.create("http://root.url")).returnStringOnGetLink(serializeCollection(collection));
        CjClient client = new CjClient(httpClient);
        Collection result = client.read(URI.create("http://root.url"));

        Assert.assertNotNull(result);
    }

    @Test(expected = ElementNotFoundException.class)
    public void selectingNonExistingQueryThrowsException() {
        Collection collection = collection();
        DummyHTTPClient httpClient = new DummyHTTPClient();
        CjClient client = new CjClient(httpClient);
        client.use(collection);

        client.selectQuery("foo");
    }

    @Test
    public void parserCreatesQuery() {
        Collection collection = collection();

        DummyHTTPClient httpClient = new DummyHTTPClient();
        httpClient.expectGetLinkWith(URI.create("http://test.com/search?name=Max"))
                .returnStringOnGetLink(serializeCollection(collection()));

        CjClient cjClient = new CjClient(httpClient);
        cjClient.use(collection);


        Query query = cjClient.selectQuery("search").set("name", "Max");

        List<Person> personList = cjClient.follow(query).convert(new ReadTransformation<Person>() {
            @Override
            public Person convert(Item item) {
                Person p = new Person();

                String name = item.getString("name");
                if (name != null) {
                    p.firstname = name;
                }
                String surname = item.getString("surname");
                if (surname != null) {
                    p.lastname = surname;
                }

                return p;
            }
        });

        Assert.assertEquals(1, personList.size());
        Assert.assertEquals("Max", personList.get(0).firstname);
        Assert.assertEquals("Musterman", personList.get(0).lastname);
    }

    @Test
    public void testAddItemToCollection() {
        Collection collection = collection();
        Template template = collection.getTemplate();

        Item content = new Item(Arrays.asList(new DataEntry("foo", "a"), new DataEntry("bar", "b"), new DataEntry("batz", "c")));
        template.fill(content);


        DummyHTTPClient http = new DummyHTTPClient();
        http.expect(POST).on(URI.create(BASE_URL)).withHeader("Content-Type", "application/vnd.collection+json").body(
                "{\"template\" : {\n" +
                        "      \"data\" : [\n" +
                        "        {\"name\" : \"foo\", \"value\" : \"a\"},\n" +
                        "        {\"name\" : \"bar\", \"value\" : \"b\"},\n" +
                        "        {\"name\" : \"batz\", \"value\" : \"c\"},\n" +
                        "      ]\n" +
                        "    }" +
                        "}"
        );
        http.respondWithURI(URI.create(BASE_URL + "/1"));
        http.expectGetLinkWith(URI.create(BASE_URL + "/1"));

        String resultJson = readFile("examples/full-collection.json");
        http.returnStringOnGetLink(resultJson);
        CjClient client = new CjClient(http);
        client.use(collection);
        Collection resultCollection = client.addItem(template);

        Assert.assertNotNull(resultCollection);

    }

    private static class Person {
        String firstname;
        String lastname;

        public String toString() {
            return firstname + " " + lastname;
        }
    }

    private Collection collection() {
        Item item = new Item(URI.create("http://test.com/people/1"), null);

        item.addData(new DataEntry("name", "Max"));
        item.addData(new DataEntry("surname", "Musterman"));

        Query query = new Query(URI.create("http://test.com/search"), "search", "", Arrays.asList(new DataEntry("name"), new DataEntry("surname")));
        Template template = new Template(Arrays.asList(new DataEntry("foo"), new DataEntry("bar"), new DataEntry("batz")));


        return new Collection(URI.create(BASE_URL), Arrays.asList(item), Arrays.asList(query), null, template);
    }
}
