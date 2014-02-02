package de.fesere.hypermedia.cj.example.employeeDB;

import de.fesere.hypermedia.cj.http.HTTPClient;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class ApacheClient implements HTTPClient {

    private HttpClient client = new HttpClient();

    @Override
    public String getLink(URI uri) {

        GetMethod get = new GetMethod(uri.toString());
        get.addRequestHeader("Accept", "application/vnd.collection+json");

        return executeStringRequest(get);
    }

    private String executeStringRequest(HttpMethod method) {
        try {
            executeAndCheckStatus(method);

            return method.getResponseBodyAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URI post(URI uri, String body, Map<String, String> header) {
        PostMethod post = new PostMethod(uri.toString());

        addHeaderToRequest(header, post);

        return executeURIrequest(post);
    }

    @Override
    public String put(URI uri, String body, Map<String, String> headder) {

        PutMethod put = new PutMethod(uri.toString());

        addHeaderToRequest(headder, put);

        return executeStringRequest(put);
    }

    private URI executeURIrequest(HttpMethod request) {
        try {
            executeAndCheckStatus(request);

            return extractURIfromHeader(request);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeAndCheckStatus(HttpMethod request) throws IOException {
        client.executeMethod(request);

        checkResponseCode(request);
    }

    private URI extractURIfromHeader(HttpMethod request) {
        String responseUri = request.getResponseHeader("Location").getValue();

        return URI.create(responseUri);
    }

    private void checkResponseCode(HttpMethod request) throws IOException {
        if(request.getStatusCode() != 201 || request.getStatusCode() != 200) {
            throw new RuntimeException("Did not receive success response: " + request.getStatusCode() + " body: " + request.getResponseBodyAsString());
        }
    }

    private void addHeaderToRequest(Map<String, String> header, EntityEnclosingMethod request) {
        for(Map.Entry<String,String> entry : header.entrySet()) {
            request.addRequestHeader(entry.getKey(), entry.getValue());
        }
    }
}
