package de.fesere.hypermedia.cj.model;


import com.fasterxml.jackson.annotation.*;
import de.fesere.hypermedia.cj.exceptions.CollectionHasErrorsException;
import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.model.transformer.ReadTransformation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("collection")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"version", "href", "links", "error", "items", "queries", "template"})
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

    public Collection(URI href, List<Item> items, List<Query> queries, List<Link>links, Template template) {
        this(href);
        if(items != null) {
            this.items.addAll(items);
        }
        if(queries != null) {
            this.queries.addAll(queries);
        }
        if(links != null) {
            this.links.addAll(links);
        }
        this.template = template;
    }

    public URI getHref() {
        return this.href;
    }

    public String getVersion() {
        return "1.0";
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


    public <T> List<T> convert(ReadTransformation<T> transformer) {
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

        return findByRel(links, rel);


    }

    private <T extends Linkable> T findByRel(List<T> elements, String rel) {
        for(T linkable : elements ) {
            if(linkable.getRel().equalsIgnoreCase(rel)) {
                return linkable;
            }
        }

        throw new ElementNotFoundException("Did not find link '"+rel+"' in collection " + href );
    }

    public Query getQuery(String rel) {
        throwExceptionOnError();

        return findByRel(queries, rel);
    }

    public Template getTemplate() {
        throwExceptionOnError();
        return template;
    }
}
