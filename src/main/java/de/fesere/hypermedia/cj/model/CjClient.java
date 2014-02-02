package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.http.HTTPClient;
import de.fesere.hypermedia.cj.serialization.Serializer;
import de.fesere.hypermedia.cj.serialization.Wrapper;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CjClient {

    private final HTTPClient httpClient;
    private Serializer serializer = new Serializer();


    public CjClient(HTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    public Collection follow(Query query) {

        URI queryURI = query.buildURI();

        return readCollection(queryURI);
    }

    public Collection readCollection(URI uri) {
        String collectionJson = httpClient.getLink(uri);

        return deserializeCollection(collectionJson);
    }

    private Collection deserializeCollection(String collectionJson) {
        return (Collection) serializer.deserialize(collectionJson, Wrapper.class).getElement();
    }

    public Collection addItem(URI href,Template template) {
        String body = serializeBody(template);

        URI result = httpClient.post(href, body, createHeader());

        return readCollection(result);
    }

    public Collection updateItem(URI itemHref, Template template) {
        String body = serializeBody(template);

        String json = httpClient.put(itemHref, body, createHeader());

        return  deserializeCollection(json);
    }

    private String serializeBody(Template template) {
        return serializer.serialize(new Wrapper<>(template));
    }

    private Map<String, String> createHeader() {
        Map<String, String> header =  new HashMap<>();
        header.put("Content-Type", "application/vnd.collection+json");
        return header;
    }
}
