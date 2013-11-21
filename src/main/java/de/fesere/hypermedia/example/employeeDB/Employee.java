package de.fesere.hypermedia.example.employeeDB;

public class Employee {
    private final int nr;
    private final String birthday;
    private final String firstName;
    private final String lastName;
    private final String gender;

    public Employee (int nr, String birthday, String firstName, String lastName, String gender) {
        this.nr = nr;
        this.birthday = birthday;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String toString() {
        return "{firstName " + firstName + ", lastName " + lastName + ", gender " + gender + ", birthday " + birthday + " , nr " + nr + "}";
    }
}
