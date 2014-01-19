package de.fesere.hypermedia.cj.example.employeeDB.employeeDB.transformations;

import de.fesere.hypermedia.cj.example.employeeDB.employeeDB.Department;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.transformer.ReadTransformation;

public class DepratmentTransformer implements ReadTransformation<Department> {
    @Override
    public Department convert(Item item) {
        return new Department(item.getString("dept_no"), item.getString("dept_name"), item.getHref());
    }
}

