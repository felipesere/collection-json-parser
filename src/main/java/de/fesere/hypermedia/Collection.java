package de.fesere.hypermedia;


import de.fesere.hypermedia.exceptions.CollectionHasErrorsException;
import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonTypeName("collection")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {

    private URI href;
    private List<Item>  items   = new ArrayList<Item>();
    private List<Link>  links   = new ArrayList<Link>();
    private List<Query> queries = new ArrayList<Query>();
    private Template template;
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

        return new ArrayList<Item>(items);
    }

    public List<Link> getLinks() {
        throwExceptionOnError();

        return new ArrayList<Link>(links);
    }

    private void throwExceptionOnError() {
        if(hasError()) {
            throw new CollectionHasErrorsException(getError());
        }
    }

    public List<Query> getQueries() {
        throwExceptionOnError();

        return new ArrayList<Query>(queries);
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public void setError(Error error) {
        this.error = error;
    }


    public Template getTemplate() {
        throwExceptionOnError();
        return template;
    }

    public <T> List<T> convert(Transformation<T> transformer) {
        throwExceptionOnError();

        List<T> result = new ArrayList<T>(items.size());

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

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
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

        for(Query query :queries) {
            if(query.getRel().equalsIgnoreCase(rel)) {
                return query;
            }
        }

        return null;
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }

    public Iterator<Collection> getIterator(Client client) {
       throwExceptionOnError();

       return new CollectionIterator(this, client);

    }

    private class CollectionIterator implements Iterator<Collection> {

        private Collection context;
        private Client client;

        public CollectionIterator(Collection context, Client client) {
            this.context = context;
            this.client = client;
        }

        @Override
        public boolean hasNext() {
            return context != null && context.getLink("next") != null;
        }

        @Override
        public Collection next() {

            URI nextUri = context.getLink("next").getHref();

            Collection next = client.read(nextUri);
            context = next;

            return next;
        }

        @Override
        public void remove() {
        }
    }
}
