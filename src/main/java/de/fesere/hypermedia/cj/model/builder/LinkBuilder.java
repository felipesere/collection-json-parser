package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.Link;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class LinkBuilder {

    private UriConstructor uriConstructor;
    private List<Link> links = new LinkedList<>();

    public LinkBuilder(URI base) {
        uriConstructor = new UriConstructor(base);
    }

    public LinkBuilder addLink(String rel, URI uri) {
        return addLink(rel, uri, null);
    }

    public LinkBuilder addLink(String rel, String relativeURL) {
        return addLink(rel, relativeURL, null);
    }

    public LinkBuilder addLink(String rel, String relativeURL, String prompt) {
        URI absoluteURL = uriConstructor.buildAbsoluteHrefFromRelative(relativeURL);
        return addLink(rel, absoluteURL, prompt);
    }


    public LinkBuilder addLink(String rel, URI uri, String prompt) {
        links.add(new Link(rel, uri, prompt));
        return this;
    }


    public List<Link> build() {
        return new LinkedList<>(links);
    }
}
