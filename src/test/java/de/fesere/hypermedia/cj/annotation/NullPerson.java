package de.fesere.hypermedia.cj.annotation;


import de.fesere.hypermedia.cj.transformer.annotations.Data;
import de.fesere.hypermedia.cj.transformer.annotations.ItemConfig;
import de.fesere.hypermedia.cj.transformer.annotations.NullWriteStrategy;

@ItemConfig(writeNull = NullWriteStrategy.AS_NULL)
public class NullPerson extends BasePerson {

    public NullPerson(@Data(name = "name") String name,
                      @Data(name = "age")  int age,
                      @Data(name = "id")   int id,
                      @Data(name = "isAdmin") boolean isAdmin,
                      @Data(name = "someValue") double someValue) {
        super(name, age, id, isAdmin, someValue);
    }
}
