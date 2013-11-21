package de.fesere.hypermedia.exceptions;

import de.fesere.hypermedia.Error;

public class CollectionHasErrorsException extends RuntimeException {

    public CollectionHasErrorsException(Error error) {
        super(error.getTitle() + "(" + error.getCode()+ ") :"  + error.getMessage());
    }


}
