package de.fesere.hypermedia.cj.example.employeeDB.transformations;

import de.fesere.hypermedia.cj.example.employeeDB.Employee;
import de.fesere.hypermedia.cj.model.data.StringDataEntry;
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
        item.addData(new StringDataEntry("birth_date", input.getBirthday()));
        item.addData(new StringDataEntry("first_name", input.getFirstName()));
        item.addData(new StringDataEntry("last_name", input.getLastName()));
        item.addData(new StringDataEntry("gender", input.getGender()));

        return item;
    }
}
