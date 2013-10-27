package de.fesere.hypermedia.http;

import java.net.URI;

public interface HttpClient {

    String getLink(URI uri);
}
