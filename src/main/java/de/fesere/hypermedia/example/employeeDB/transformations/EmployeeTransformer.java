package de.fesere.hypermedia.example.employeeDB.transformations;

import de.fesere.hypermedia.Item;
import de.fesere.hypermedia.Transformation;
import de.fesere.hypermedia.example.employeeDB.Employee;

public class EmployeeTransformer implements Transformation<Employee> {

    @Override
    public Employee convert(Item item) {

        return new Employee(item.getInt("emp_no"),
                item.getString("birth_date"),
                item.getString("first_name"),
                item.getString("last_name"),
                item.getString("gender"));
    }
}
