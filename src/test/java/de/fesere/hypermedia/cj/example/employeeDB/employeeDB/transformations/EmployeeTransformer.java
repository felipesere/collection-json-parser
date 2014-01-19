package de.fesere.hypermedia.cj.example.employeeDB.employeeDB.transformations;

import de.fesere.hypermedia.cj.example.employeeDB.employeeDB.Employee;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.Transformation;

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
