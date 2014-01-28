package de.fesere.hypermedia.cj.model.server;

import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.Query;
import de.fesere.hypermedia.cj.model.Template;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class CollectionBuilder {

    private UriConstructor uriConstructor;
    private Template temaplate;
    private List<Query> queries = new LinkedList<>();

    public CollectionBuilder(URI href) {
        if(href == null) {
            throw new IllegalArgumentException("URI must not be null!");
        }
        uriConstructor = new UriConstructor(href);
    }

    public CollectionBuilder addTemplate(Template temaplte) {
        this.temaplate = temaplte;
        return this;
    }

    public Collection build() {
        return new Collection(uriConstructor.getBase(), null, queries, null, temaplate);
    }


    public CollectionBuilder addQuery(Query query) {
        queries.add(query);
        return this;
    }

    public CollectionBuilder addQuery(Query query, String relativURL) {
        URI absoluteURL = uriConstructor.buildAbsoluteHrefFromRelative(relativURL);

        Query temp = new Query(absoluteURL, query.getRel(), query.getPrompt(), query.getData());

       return addQuery(temp);
    }


}
