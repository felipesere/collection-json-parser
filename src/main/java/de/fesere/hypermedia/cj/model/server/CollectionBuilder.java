package de.fesere.hypermedia.cj.model.server;

import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.Query;
import de.fesere.hypermedia.cj.model.Template;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class CollectionBuilder {

    private final URI href;
    private Template temaplate;
    private List<Query> queries = new LinkedList<>();

    public CollectionBuilder(URI href) {
        if(href == null) {
            throw new IllegalArgumentException("URI must not be null!");
        }
        this.href = href;
    }

    public CollectionBuilder addTemplate(Template temaplte) {
        this.temaplate = temaplte;
        return this;
    }

    public Collection build() {
        return new Collection(href, null, queries, null, temaplate);
    }


    public CollectionBuilder addQuery(Query query) {
        if(query == null) {
            throw new IllegalArgumentException("Query must not be null!");
        }
        queries.add(query);
        return this;
    }

    public CollectionBuilder addQuery(Query query, String relativURL) {
        if(query == null) {
            throw new IllegalArgumentException("Query must not be null!");
        }
        URI absoluteURL = buildAbsoluteHrefFromRelative(relativURL);

        Query temp = new Query(absoluteURL, query.getRel(), query.getPrompt(), query.getData());

       return addQuery(temp);
    }

    private URI buildAbsoluteHrefFromRelative(String relativURL) {
        String urlTemp = hrefWithoutTrailingSlash();
        return URI.create(urlTemp + relativURL);
    }

    private String hrefWithoutTrailingSlash() {
        String hrefTemp = href.toString();
        if(hrefTemp.endsWith("/")) {
            return removeTrailingSlash(hrefTemp);
        }
        return hrefTemp;
    }

    private String removeTrailingSlash(String hrefTemp) {
        return hrefTemp.substring(0, hrefTemp.lastIndexOf("/"));
    }
}
