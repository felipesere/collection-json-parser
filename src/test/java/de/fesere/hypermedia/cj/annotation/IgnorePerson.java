package de.fesere.hypermedia.cj.annotation;

import de.fesere.hypermedia.cj.annotations.ItemConfig;
import de.fesere.hypermedia.cj.annotations.NullWriteStrategy;

@ItemConfig(writeNull = NullWriteStrategy.IGNORE)
public class IgnorePerson extends BasePerson {
    public IgnorePerson(String name, int age, int id, boolean isAdmin, double someValue) {
        super(name, age, id, isAdmin, someValue);
    }
}
