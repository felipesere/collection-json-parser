package de.fesere.hypermedia.cj.http;

import java.net.URI;

public interface HTTPClient {

    String getLink(URI uri);

    URI post(URI href, String body);
}
