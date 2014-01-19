package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.http.HTTPClient;
import de.fesere.hypermedia.cj.model.serialization.ObjectMapperConfig;
import de.fesere.hypermedia.cj.model.serialization.Wrapper;

import java.io.IOException;
import java.net.URI;

public class CjClient {

    private Collection collection;
    private final HTTPClient httpClient;
    private final ObjectMapper mapper;


    public CjClient(HTTPClient httpClient) {
        this.httpClient = httpClient;
        mapper = (new ObjectMapperConfig()).getConfiguredObjectMapper();

    }

    public CjClient use(Collection collection) {
        this.collection = collection;
        return this;
    }

    public Collection follow(Query query) {

        URI queryURI = query.buildURI();
        String json = httpClient.getLink(queryURI);

        return (Collection) deserialize(json, Wrapper.class).element;
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

        return (Collection) deserialize(collectionJson, Wrapper.class).element;
    }

    public Collection addItem(Template template) {
        String body = serialize(template);

        URI result = httpClient.post(collection.getHref(), body);

        return read(result);
    }

    private <T> T deserialize(String input, Class<T> responseClass) {

        try {
            return mapper.readValue(input, responseClass);
        } catch (IOException e) {
            throw new RuntimeException("Could not deseriliaze input to " + responseClass.getName(),e);
        }
    }

    private String serialize(Object obj) {

        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException("Could not serilize " + obj.getClass().getName() + " to string",e );
        }

    }
}
