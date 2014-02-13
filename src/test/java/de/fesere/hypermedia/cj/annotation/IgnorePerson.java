package de.fesere.hypermedia.cj.annotation;

import de.fesere.hypermedia.cj.transformer.annotations.ItemConfig;
import de.fesere.hypermedia.cj.transformer.annotations.NullWriteStrategy;

@ItemConfig(writeNull = NullWriteStrategy.IGNORE)
public class IgnorePerson extends BasePerson {
    public IgnorePerson(String name, int age, int id, boolean isAdmin, double someValue) {
        super(name, age, id, isAdmin, someValue);
    }
}
