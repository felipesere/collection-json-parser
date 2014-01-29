package de.fesere.hypermedia.cj.model.server;

import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.Error;

import java.net.URI;

public class ErrorBuilder {

    private String title = "An error occured";
    private String status = "500";
    private String message = "There was an error processing the message";
    private URI base;

    public ErrorBuilder(URI base) {
        this.base = base;
    }

    public ErrorBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ErrorBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public ErrorBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public Collection build() {
        Error error = new Error(title, "" + status, message);

        return new Collection(base, error);
    }
}
