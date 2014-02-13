package de.fesere.hypermedia.cj.transformer.annotations;

public enum NullWriteStrategy {
    AS_EMPTY,
    AS_NULL,
    IGNORE     ;

    public static NullWriteStrategy defautlNullWrite() {
        return  AS_NULL;
    }
}
