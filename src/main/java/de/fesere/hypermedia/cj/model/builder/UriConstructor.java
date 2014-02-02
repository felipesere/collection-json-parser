package de.fesere.hypermedia.cj.model.builder;

import java.net.URI;

public class UriConstructor {

    private final URI base;

    public UriConstructor(URI base) {
        this.base = base;
    }

    public URI buildAbsoluteHrefFromRelative(String relativURL) {
        String urlTemp = hrefWithoutTrailingSlash();
        String slash = requiresSlash(relativURL);
        return URI.create(urlTemp + slash + relativURL);
    }

    private String requiresSlash(String relativURL) {
        if(relativURL.startsWith("/")) {
            return "";
        }
        return "/";
    }

    private String hrefWithoutTrailingSlash() {
        String hrefTemp = base.toString();
        if(hrefTemp.endsWith("/")) {
            return removeTrailingSlash(hrefTemp);
        }
        return hrefTemp;
    }

    private String removeTrailingSlash(String hrefTemp) {
        return hrefTemp.substring(0, hrefTemp.lastIndexOf('/'));
    }

    public URI getBase() {
        return base;
    }
}
