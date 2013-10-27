package de.fesere.hypermedia;


import org.codehaus.jackson.annotate.*;

import java.net.URI;
import java.util.ArrayList;
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

    public ArrayList<Item> getItems() {
        return new ArrayList<Item>(items);
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = new ArrayList<Link>(links);
    }

    public List<Query> getQueries() {
        return queries;
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
        return template;
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public <T> List<T> convert(Transformation<T> transformer) {
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
}
