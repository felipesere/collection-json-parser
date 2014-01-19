package de.fesere.hypermedia.cj.model;


import de.fesere.hypermedia.cj.exceptions.CollectionHasErrorsException;
import org.codehaus.jackson.annotate.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("collection")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {

    @JsonProperty("href")
    private final URI href;

    @JsonProperty("items")
    private final List<Item>  items   = new ArrayList<>();

    @JsonProperty("links")
    private final List<Link>  links   = new ArrayList<>();

    @JsonProperty("queries")
    private List<Query> queries = new ArrayList<>();

    @JsonProperty("template")
    private Template template;

    @JsonProperty("error")
    private Error error;

    @JsonCreator()
    public Collection(@JsonProperty("href") URI href) {
        this.href = href;
    }

    public URI getHref() {
        return this.href;
    }

    public String getVersion() {
        return "1.0";
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        throwExceptionOnError();

        return new ArrayList<>(items);
    }

    public List<Link> getLinks() {
        throwExceptionOnError();

        return new ArrayList<>(links);
    }

    private void throwExceptionOnError() {
        if(hasError()) {
            throw new CollectionHasErrorsException(getError());
        }
    }

    public List<Query> getQueries() {
        throwExceptionOnError();

        return new ArrayList<>(queries);
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }


    public Template getTemplate() {
        throwExceptionOnError();
        return template;
    }

    public <T> List<T> convert(Transformation<T> transformer) {
        throwExceptionOnError();

        List<T> result = new ArrayList<>(items.size());

        for(Item item : items) {
            T convertedItem = transformer.convert(item);
            if(convertedItem != null) {
                result.add(convertedItem);
            }
        }

        return result;
    }

    public boolean hasError() {
        return error != null;
    }

    public Error getError() {
        return error;
    }

    public Link getLink(String rel) {
        throwExceptionOnError();

        for(Link link : links) {
            if(link.getRel().equalsIgnoreCase(rel)) {
                return link;
            }
        }

        return null;
    }

    public Query getQuery(String rel) {
        throwExceptionOnError();

        for(Query query : queries) {
            if(query.getRel().equalsIgnoreCase(rel)) {
                return query;
            }
        }

        return null;
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }
}
