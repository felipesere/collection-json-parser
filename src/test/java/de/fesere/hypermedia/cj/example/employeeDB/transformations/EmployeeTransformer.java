package de.fesere.hypermedia.cj.example.employeeDB.transformations;

import de.fesere.hypermedia.cj.example.employeeDB.Employee;
import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.transformer.TwoWayTransformer;

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
        item.addData(new DataEntry("birth_date", input.getBirthday()));
        item.addData(new DataEntry("first_name", input.getFirstName()));
        item.addData(new DataEntry("last_name", input.getLastName()));
        item.addData(new DataEntry("gender", input.getGender()));

        return item;
    }
}
