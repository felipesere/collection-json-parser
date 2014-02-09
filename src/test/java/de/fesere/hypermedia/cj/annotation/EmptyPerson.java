package de.fesere.hypermedia.cj.annotation;

import de.fesere.hypermedia.cj.annotations.ItemConfig;
import de.fesere.hypermedia.cj.annotations.NullWriteStrategy;

@ItemConfig(writeNull = NullWriteStrategy.AS_EMPTY)
public class EmptyPerson extends BasePerson {
    public EmptyPerson(String name, int age, int id, boolean isAdmin, double someValue) {
        super(name, age, id, isAdmin, someValue);
    }
}
