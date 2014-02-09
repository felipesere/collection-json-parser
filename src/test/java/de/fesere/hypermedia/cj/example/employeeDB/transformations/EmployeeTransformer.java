package de.fesere.hypermedia.cj.example.employeeDB.transformations;

import de.fesere.hypermedia.cj.example.employeeDB.Employee;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.transformer.TwoWayTransformer;

import static de.fesere.hypermedia.cj.model.builder.DataEntryFactory.create;

public class EmployeeTransformer extends TwoWayTransformer<Employee> {

    @Override
    public Employee transform(Item item) {

        return new Employee(item.getInt("emp_no"),
                item.getString("birth_date"),
                item.getString("first_name"),
                item.getString("last_name"),
                item.getString("gender"));
    }

    @Override
    public Item transform(Employee input) {
        Item item = new Item();
        item.addData(create("birth_date", input.getBirthday()));
        item.addData(create("first_name", input.getFirstName()));
        item.addData(create("last_name", input.getLastName()));
        item.addData(create("gender", input.getGender()));

        return item;
    }
}
