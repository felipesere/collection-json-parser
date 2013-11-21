package de.fesere.hypermedia;

import de.fesere.hypermedia.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.http.HTTPClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Client {

    private Collection collection;
    private HTTPClient httpClient;


    public Client(HTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    public Client use(Collection collection) {
        this.collection = collection;
        return this;
    }

    public Collection follow(Query query) {

        try {
            URI href = new URI(query.getHref().toString() + "?"+query.getDataForUri());
            String json = httpClient.getLink(href);

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json, Collection.class);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
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
            System.out.println(collectionJson);
            throw new RuntimeException(e);
        }
    }
}
