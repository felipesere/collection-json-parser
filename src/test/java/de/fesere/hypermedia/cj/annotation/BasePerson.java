package de.fesere.hypermedia.cj.annotation;

import de.fesere.hypermedia.cj.transformer.annotations.Data;


public abstract class BasePerson {

    @Data(name = "name", prompt = "The persons name")
    private String name;

    @Data(name = "age")
    private int age;

    @Data(name = "id")
    private int id;

    @Data(name ="isAdmin", prompt = "Is the person a sys-admin")
    private boolean admin;

    @Data(name ="someDouble")
    private double someValue;

    @Data(name ="foo", prompt = "Doo dough")
    private Integer foo = null;

    public BasePerson(@Data(name = "name") String name, int age, int id, boolean isAdmin, double someValue){
        this.name = name;
        this.age = age;
        this.id = id;
        admin = isAdmin;
        this.someValue = someValue;
        admin = true;
    }


    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSomeValue() {
        return someValue;
    }
}
