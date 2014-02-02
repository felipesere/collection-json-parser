package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.Link;
import de.fesere.hypermedia.cj.model.Query;
import de.fesere.hypermedia.cj.model.Template;
import de.fesere.hypermedia.cj.model.transformer.WriteTransformer;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class CollectionBuilder<T> {

    private final WriteTransformer<T> transformer;
    private UriConstructor uriConstructor;
    private LinkBuilder linkBuilder;
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
        linkBuilder = new LinkBuilder(href);
    }

    public CollectionBuilder(URI href) {
        this(href, null);
    }

    public CollectionBuilder addTemplate(Template temaplte) {
        this.temaplate = temaplte;
        return this;
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

    public LinkBuilder getLinkBuilder() {
        return linkBuilder;
    }

    public CollectionBuilder addObject(T object) {
        Item item = transformer.transform(object);
        if (item != null) {
            addItem(item);
        }

        return this;
    }

    public CollectionBuilder addItem(Item item) {
        items.add(item);
        return this;
    }

    public Collection build() {
        return new Collection(uriConstructor.getBase(), items, queries, linkBuilder.build(), temaplate);
    }
}
