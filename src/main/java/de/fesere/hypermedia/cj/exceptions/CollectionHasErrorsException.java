package de.fesere.hypermedia.cj.exceptions;

import de.fesere.hypermedia.cj.model.Error;

public class CollectionHasErrorsException extends RuntimeException {

    public CollectionHasErrorsException(Error error) {
        super(error.getTitle() + " (Code " + error.getCode() + ") : " + error.getMessage() + ".");
    }


}
