package de.fesere.hypermedia.example.employeeDB;

import de.fesere.hypermedia.*;
import de.fesere.hypermedia.example.employeeDB.transformations.DepratmentTransformer;
import de.fesere.hypermedia.example.employeeDB.transformations.EmployeeTransformer;
import de.fesere.hypermedia.http.ApacheClient;

import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EmployeeDB {

    Client httpClient = new Client(new ApacheClient());
    private URI START = URI.create("http://employee.herokuapp.com");

    private URI departmentsURI = null;
    private URI employeesURI;


    public EmployeeDB() {
        Collection landingCollection = httpClient.read(START);

        handlePossibleErrors(landingCollection);

        departmentsURI = landingCollection.getLink("departments").getHref();
        employeesURI = landingCollection.getLink("employees").getHref();
    }

    public void handlePossibleErrors(Collection collection) {
        if(collection.hasError()) {
            throw new RuntimeException(collection.getError().getMessage());
        }
    }

    public List<Employee> getEmployeesOfDepartment(String departmentName) {
        Collection departments = httpClient.read(departmentsURI);

       Iterator<Collection> collectionIterator = departments.getIterator(httpClient);

        while(collectionIterator.hasNext()) {
            Collection subset = collectionIterator.next();
            departments.addItems(subset.getItems());
        }


        Item foundItem = null;
        for(Item item : departments.getItems()) {
            if(item.getString("dept_name").equalsIgnoreCase(departmentName)) {
                foundItem=item;
            }
        }

        if(foundItem == null) {
            return Collections.emptyList();
        }

        Collection department = httpClient.read(foundItem.getHref());

        Collection employees = httpClient.read(department.getLink("employees").getHref());

        Iterator<Collection> employeeIter = employees.getIterator(httpClient);
        while(employeeIter.hasNext()) {
            employees.addItems(employeeIter.next().getItems());
            System.out.println(employees.getItems().size());
        }

        return employees.convert(new EmployeeTransformer());
    }

    public List<Department> getAllDepartments() {
        Collection departments = httpClient.read(departmentsURI);

        Iterator<Collection> iterator = departments.getIterator(httpClient);
        while(iterator.hasNext()) {
            departments.addItems(iterator.next().getItems());
        }

        handlePossibleErrors(departments);

        return departments.convert(new DepratmentTransformer());
    }

    public List<Employee> getAllEmployees() {
        Collection employees = httpClient.read(employeesURI);

        handlePossibleErrors(employees);

        return employees.convert(new EmployeeTransformer());
    }

    public List<Employee> findEmploy(String name) {
        Collection employees = httpClient.read(employeesURI);
        Query searchQuery = employees.getQuery("search");

        Query filledQuery = searchQuery.set("name", name);

        return httpClient.follow(filledQuery).convert(new EmployeeTransformer());
    }
}
