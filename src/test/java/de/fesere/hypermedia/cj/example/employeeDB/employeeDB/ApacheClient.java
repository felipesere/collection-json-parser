package de.fesere.hypermedia.cj.example.employeeDB.employeeDB;

import de.fesere.hypermedia.cj.http.HTTPClient;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.net.URI;

public class ApacheClient implements HTTPClient {
    private String collectionJson =  "application/vnd.collection+json";

    @Override
    public String getLink(URI uri) {

        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(uri.toString());
        get.addRequestHeader("Accept", collectionJson);

        try {
            client.executeMethod(get);

            return get.getResponseBodyAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URI post(URI uri, String body) {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(uri.toString());
        post.addRequestHeader("Content-Type", collectionJson);

        try {
            client.executeMethod(post);

            if(post.getStatusCode() == 201) {
                String responseUri = post.getResponseHeader("Location").getValue();

                return URI.create(responseUri);
            }
            else {
                throw new RuntimeException("Server did not respond with 201: " + post.getStatusCode() + " body: " + post.getResponseBodyAsString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
