package de.fesere.hypermedia.cj.http;

import junit.framework.Assert;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import java.net.URI;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;

public class DummyHTTPClient implements HTTPClient {

    private URI expectedURI = null;
    private URI expectedGETuri = null;
    private String getLinkResponse = "";
    private String expectedVerb;
    private String headerName;
    private String headerValue;
    private String expectedContent;
    private URI uriToRespond;

    public DummyHTTPClient expectGetLinkWith(URI uri) {
        expectedGETuri = uri;
        return this;
    }

    public DummyHTTPClient returnStringOnGetLink(String s) {
        getLinkResponse = s;
        return this;
    }

    @Override
    public String getLink(URI actual) {

        if(expectedGETuri != null) {
            Assert.assertEquals("getLink called with unexpected URI", expectedGETuri, actual);
        }
        return getLinkResponse;
    }

    @Override
    public URI post(URI href, String body, Map<String, String> header) {
        assertThat(expectedVerb, is("POST"));
        assertThat(expectedURI, is(href));
        assertThat(header, hasEntry(headerName, headerValue));
        try {
            JSONAssert.assertEquals(expectedContent, body, false);
        } catch (JSONException e) {
            Assert.fail(e.getMessage());

        }
        return uriToRespond;
    }

    public DummyHTTPClient expect(String verb) {
        expectedVerb = verb;
        return this;
    }

    public DummyHTTPClient on(URI expectedUri) {
        this.expectedURI = expectedUri;
        return this;
    }

    public DummyHTTPClient withHeader(String name, String value) {
        headerName = name;
        headerValue= value;
        return this;
    }

    public DummyHTTPClient body(String content) {
        expectedContent = content;
        return this;
    }

    public DummyHTTPClient respond() {
        return this;
    }

    public DummyHTTPClient withStatus(int status) {
        return this;
    }

    public void respondWithURI(URI uri) {
        uriToRespond = uri;
    }
}
