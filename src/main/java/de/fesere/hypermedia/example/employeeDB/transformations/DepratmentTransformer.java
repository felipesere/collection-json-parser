package de.fesere.hypermedia.example.employeeDB.transformations;

import de.fesere.hypermedia.Item;
import de.fesere.hypermedia.Transformation;
import de.fesere.hypermedia.example.employeeDB.Department;

public class DepratmentTransformer implements Transformation<Department> {
    @Override
        public Department convert(Item item) {
            return new Department(item.getString("dept_no"), item.getString("dept_name"), item.getHref());
        }
    }

