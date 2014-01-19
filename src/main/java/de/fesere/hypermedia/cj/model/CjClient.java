package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.http.HTTPClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URI;

public class CjClient {

    private Collection collection;
    private final HTTPClient httpClient;


    public CjClient(HTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    public CjClient use(Collection collection) {
        this.collection = collection;
        return this;
    }

    public Collection follow(Query query) {

        try {

            URI queryURI = query.buildURI();
            String json = httpClient.getLink(queryURI);

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json, Collection.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public Query selectQuery(String search) {
        for(Query query : collection.getQueries()) {
            if(query.getRel().equals(search)) {
                return query;
            }
        }

        throw new ElementNotFoundException("Query '"+search+"' not found" );
    }

    public Collection read(URI uri) {
        String collectionJson = httpClient.getLink(uri);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(collectionJson, Collection.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
