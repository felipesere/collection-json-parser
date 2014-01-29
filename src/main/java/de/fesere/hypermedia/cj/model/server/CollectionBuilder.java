package de.fesere.hypermedia.cj.model.server;

import de.fesere.hypermedia.cj.model.*;
import de.fesere.hypermedia.cj.model.transformer.WriteTransformer;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class CollectionBuilder<T> {

    private final WriteTransformer<T> transformer;
    private UriConstructor uriConstructor;
    private Template temaplate;
    private List<Query> queries = new LinkedList<>();
    private List<Link> links = new LinkedList<>();
    private List<Item> items = new LinkedList<>();

    public CollectionBuilder(URI href, WriteTransformer<T> transformer) {
        this.transformer = transformer;
        if (href == null) {
            throw new IllegalArgumentException("URI must not be null!");
        }
        uriConstructor = new UriConstructor(href);
    }

    public CollectionBuilder(URI href) {
        this(href, null);
    }

    public CollectionBuilder addTemplate(Template temaplte) {
        this.temaplate = temaplte;
        return this;
    }

    public Collection build() {
        return new Collection(uriConstructor.getBase(), items, queries, links, temaplate);
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

    public CollectionBuilder addLink(String rel, URI uri) {
        links.add(new Link(rel, uri));
        return this;
    }

    public CollectionBuilder addLink(String rel, String relativeURL) {
        URI absoluteURL = uriConstructor.buildAbsoluteHrefFromRelative(relativeURL);
        return addLink(rel, absoluteURL);
    }


    public CollectionBuilder addItem(T thing) {
        Item item = transformer.convert(thing);
        if (item != null) {
            items.add(item);
        }

        return this;
    }
}
