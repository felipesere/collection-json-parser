package de.fesere.hypermedia.example.employeeDB;

import java.net.URI;

public class Department {
    private final String nr;
    private final String name;
    private final URI link;

    public Department(String nr, String name, URI link) {
        this.nr = nr;
        this.name = name;
        this.link = link;
    }

    public URI getLink(){
        return link;
    }


    public String toString() {
        return "Department [nr: " + nr + ", name:" + name+"]";
    }
}
