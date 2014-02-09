package de.fesere.hypermedia.cj.annotation;


import de.fesere.hypermedia.cj.annotations.ItemConfig;
import de.fesere.hypermedia.cj.annotations.NullWriteStrategy;

@ItemConfig(writeNull = NullWriteStrategy.AS_NULL)
public class NullPerson extends BasePerson {

    public NullPerson(String name, int age, int id, boolean isAdmin, double someValue) {
        super(name, age, id, isAdmin, someValue);
    }
}
