package de.fesere.hypermedia.cj.exceptions;

import java.net.URI;

public class PropertyNotFoundException extends MalformedDataValueException {
    public PropertyNotFoundException(String name, URI href, Class type) {
        super("Did not find property '" + name + "' as " + type.getSimpleName() +" in " + href);
    }
}
