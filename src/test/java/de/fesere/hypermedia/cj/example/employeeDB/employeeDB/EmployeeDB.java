package de.fesere.hypermedia.cj.example.employeeDB.employeeDB;

import de.fesere.hypermedia.cj.example.employeeDB.employeeDB.transformations.DepratmentTransformer;
import de.fesere.hypermedia.cj.example.employeeDB.employeeDB.transformations.EmployeeTransformer;
import de.fesere.hypermedia.cj.model.*;

import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDB {

    private final CjClient httpCjClient = new CjClient(new ApacheClient());

    private URI departmentsURI = null;
    private final URI employeesURI;


    public EmployeeDB() {
        URI START = URI.create("http://employee.herokuapp.com");
        Collection rootCollection = httpCjClient.readCollection(START);

        handlePossibleErrors(rootCollection);

        departmentsURI = rootCollection.getLink("departments").getHref();
        employeesURI = rootCollection.getLink("employees").getHref();
    }

    void handlePossibleErrors(Collection collection) {
        if(collection.hasError()) {
            throw new RuntimeException(collection.getError().getMessage());
        }
    }

    public void addNewEmployee(Employee employee) {
        Collection employees = httpCjClient.readCollection(employeesURI);

        Template template = employees.getTemplate();
        template.fill(new EmployeeTransformer().transform(employee));

        httpCjClient.addItem(employees.getHref(), template);
    }

    public List<Employee> getEmployeesOfDepartment(String departmentName) {
        Collection departments = httpCjClient.readCollection(departmentsURI);

       Iterator<Collection> collectionIterator = new CollectionIterator(departments, httpCjClient);

        List<Item> items = new LinkedList<>();
        while(collectionIterator.hasNext()) {
            Collection subset = collectionIterator.next();
            items.addAll(subset.getItems());
        }


        Item foundItem = null;
        for(Item item : items) {
            if(item.getString("dept_name").equalsIgnoreCase(departmentName)) {
                foundItem=item;
            }
        }

        if(foundItem == null) {
            return Collections.emptyList();
        }

        Collection department = httpCjClient.readCollection(foundItem.getHref());

        Collection employees = httpCjClient.readCollection(department.getLink("employees").getHref());

        Iterator<Collection> employeeIter = new CollectionIterator(employees, httpCjClient);
        List<Item> employeeItems = new LinkedList<>();
        while(employeeIter.hasNext()) {
            employeeItems.addAll(employeeIter.next().getItems());
            System.out.println(employeeItems.size());
        }

        Collection finalEmployees = new Collection(employees.getHref(), employeeItems, employees.getQueries(), employees.getLinks(), employees.getTemplate());

        return finalEmployees.transform(new EmployeeTransformer());
    }

    public List<Department> getAllDepartments() {
        Collection departments = httpCjClient.readCollection(departmentsURI);

        Iterator<Collection> iterator = new CollectionIterator(departments, httpCjClient);
        List<Item> items = new LinkedList<>();
        while(iterator.hasNext()) {
            System.out.println("itearion!");
            items.addAll(iterator.next().getItems());
        }

        handlePossibleErrors(departments);

        Collection finalCollection = new Collection(departments.getHref(), items, departments.getQueries(), departments.getLinks(), departments.getTemplate());

        return finalCollection.transform(new DepratmentTransformer());
    }

    public List<Employee> getAllEmployees() {
        Collection employees = httpCjClient.readCollection(employeesURI);

        handlePossibleErrors(employees);

        return employees.transform(new EmployeeTransformer());
    }

    public List<Employee> findEmployeeByName(String name) {
        Collection employees = httpCjClient.readCollection(employeesURI);
        Query searchQuery = employees.getQuery("search");

        Query filledQuery = searchQuery.set("name", name);

        return httpCjClient.follow(filledQuery).transform(new EmployeeTransformer());
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

            Collection next = cjClient.readCollection(nextUri);
            context = next;

            return next;
        }

        @Override
        public void remove() {
        }
    }
}
