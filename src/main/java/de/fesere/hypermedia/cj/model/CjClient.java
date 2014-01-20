package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.http.HTTPClient;
import de.fesere.hypermedia.cj.model.serialization.Serializer;
import de.fesere.hypermedia.cj.model.serialization.Wrapper;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CjClient {

    private Collection collection;
    private final HTTPClient httpClient;
    private Serializer serializer = new Serializer();


    public CjClient(HTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    public CjClient use(Collection collection) {
        this.collection = collection;
        return this;
    }

    public Collection follow(Query query) {

        URI queryURI = query.buildURI();

        return read(queryURI);
    }

    public Query selectQuery(String search) {
        for (Query query : collection.getQueries()) {
            if (query.getRel().equals(search)) {
                return query;
            }
        }

        throw new ElementNotFoundException("Query '" + search + "' not found");
    }

    public Collection read(URI uri) {
        String collectionJson = httpClient.getLink(uri);

        return (Collection) serializer.deserialize(collectionJson, Wrapper.class).element;
    }

    public Collection addItem(Template template) {
        String body = serializer.serialize(new Wrapper<>(template));

        URI result = httpClient.post(collection.getHref(), body, createHeader());

        return read(result);
    }

    private Map<String, String> createHeader() {
        Map<String, String> header =  new HashMap<>();
        header.put("Content-Type", "application/vnd.collection+json");
        return header;
    }
}
