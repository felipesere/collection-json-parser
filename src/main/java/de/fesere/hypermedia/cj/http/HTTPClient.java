package de.fesere.hypermedia.cj.http;

import java.net.URI;
import java.util.Map;

public interface HTTPClient {

    String getLink(URI uri);

    URI post(URI href, String body, Map<String, String> header);

    String put(URI href, String body, Map<String, String> headder);
}
