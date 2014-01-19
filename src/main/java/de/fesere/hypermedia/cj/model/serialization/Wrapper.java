package de.fesere.hypermedia.cj.model.serialization;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = WrapperSerializer.class)
@JsonDeserialize(using = WrapperDeserializer.class)
public class Wrapper<T> {

    public T element;


    public Wrapper(T element) {
        this.element = element;
    }
}
