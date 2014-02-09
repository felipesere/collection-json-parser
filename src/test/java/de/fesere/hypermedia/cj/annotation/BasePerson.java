package de.fesere.hypermedia.cj.annotation;

import de.fesere.hypermedia.cj.annotations.Data;


public abstract class BasePerson {

    @Data(value = "name", prompt = "The persons name")
    private String name;

    @Data(value = "age")
    private int age;

    @Data(value = "id")
    private int id;

    @Data(value="isAdmin", prompt = "Is the person a sys-admin")
    private boolean admin;

    @Data(value="someDouble")
    private double someValue;

    @Data(value="foo")
    Integer foo = null;

    public BasePerson(String name, int age, int id, boolean isAdmin, double someValue){
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
