package de.fesere.hypermedia.http;

import junit.framework.Assert;

import java.net.URI;

public class DummyHttpClient implements HttpClient {

    private URI expectedURI = null;
    private String getLinkResponse = "";

    public DummyHttpClient expectGetLinkWith(URI uri) {
        expectedURI = uri;
        return this;
    }

    public DummyHttpClient returnStringOnGetLink(String s) {
        getLinkResponse = s;
        return this;
    }

    @Override
    public String getLink(URI actual) {

        if(expectedURI != null) {
            Assert.assertEquals("getLink called with unexpected URI", expectedURI, actual);
        }
        return getLinkResponse;
    }
}
