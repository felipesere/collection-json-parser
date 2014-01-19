package de.fesere.hypermedia.cj.http;

import junit.framework.Assert;

import java.net.URI;

public class DummyHTTPClient implements HTTPClient {

    private URI expectedURI = null;
    private String getLinkResponse = "";

    public DummyHTTPClient expectGetLinkWith(URI uri) {
        expectedURI = uri;
        return this;
    }

    public DummyHTTPClient returnStringOnGetLink(String s) {
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
