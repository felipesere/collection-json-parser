package de.fesere.hypermedia.cj.example.employeeDB;

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
        return "{firstName " + getFirstName() + ", lastName " + getLastName() + ", gender " + getGender() + ", birthday " + getBirthday() + " , nr " + getNr() + "}";
    }

    public int getNr() {
        return nr;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
