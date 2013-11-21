package de.fesere.hypermedia.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.net.URI;

public class ApacheClient implements HTTPClient {

    @Override
    public String getLink(URI uri) {

        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(uri.toString());
        get.addRequestHeader("Accept", "application/vnd.collection+json");

        try {
            client.executeMethod(get);
            String responseBodyAsString = get.getResponseBodyAsString();

            return responseBodyAsString;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
