package de.fesere.hypermedia.cj.example.employeeDB.employeeDB;

import de.fesere.hypermedia.cj.example.employeeDB.employeeDB.transformations.DepratmentTransformer;
import de.fesere.hypermedia.cj.example.employeeDB.employeeDB.transformations.EmployeeTransformer;
import de.fesere.hypermedia.cj.model.CjClient;
import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.Query;

import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EmployeeDB {

    private final CjClient httpCjClient = new CjClient(new ApacheClient());

    private URI departmentsURI = null;
    private final URI employeesURI;


    public EmployeeDB() {
        URI START = URI.create("http://employee.herokuapp.com");
        Collection rootCollection = httpCjClient.read(START);

        handlePossibleErrors(rootCollection);

        departmentsURI = rootCollection.getLink("departments").getHref();
        employeesURI = rootCollection.getLink("employees").getHref();
    }

    void handlePossibleErrors(Collection collection) {
        if(collection.hasError()) {
            throw new RuntimeException(collection.getError().getMessage());
        }
    }

    public List<Employee> getEmployeesOfDepartment(String departmentName) {
        Collection departments = httpCjClient.read(departmentsURI);

       Iterator<Collection> collectionIterator = new CollectionIterator(departments, httpCjClient);

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

        Collection department = httpCjClient.read(foundItem.getHref());

        Collection employees = httpCjClient.read(department.getLink("employees").getHref());

        Iterator<Collection> employeeIter = new CollectionIterator(employees, httpCjClient);
        while(employeeIter.hasNext()) {
            employees.addItems(employeeIter.next().getItems());
            System.out.println(employees.getItems().size());
        }

        return employees.convert(new EmployeeTransformer());
    }

    public List<Department> getAllDepartments() {
        Collection departments = httpCjClient.read(departmentsURI);

        Iterator<Collection> iterator = new CollectionIterator(departments, httpCjClient);
        while(iterator.hasNext()) {
            System.out.println("itearion!");
            departments.addItems(iterator.next().getItems());
        }

        handlePossibleErrors(departments);

        return departments.convert(new DepratmentTransformer());
    }

    public List<Employee> getAllEmployees() {
        Collection employees = httpCjClient.read(employeesURI);

        handlePossibleErrors(employees);

        return employees.convert(new EmployeeTransformer());
    }

    public List<Employee> findEmployeeByName(String name) {
        Collection employees = httpCjClient.read(employeesURI);
        Query searchQuery = employees.getQuery("search");

        Query filledQuery = searchQuery.set("name", name);

        return httpCjClient.follow(filledQuery).convert(new EmployeeTransformer());
    }

    private class CollectionIterator implements Iterator<Collection> {

        private Collection context;
        private final CjClient cjClient;

        public CollectionIterator(Collection context, CjClient cjClient) {
            this.context = context;
            this.cjClient = cjClient;
        }

        @Override
        public boolean hasNext() {
            return context != null && context.getLink("next") != null;
        }

        @Override
        public Collection next() {

            URI nextUri = context.getLink("next").getHref();

            Collection next = cjClient.read(nextUri);
            context = next;

            return next;
        }

        @Override
        public void remove() {
        }
    }
}
