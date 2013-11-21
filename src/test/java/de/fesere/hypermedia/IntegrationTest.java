package de.fesere.hypermedia;

import de.fesere.hypermedia.example.employeeDB.Department;
import de.fesere.hypermedia.example.employeeDB.EmployeeDB;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class IntegrationTest {

    @Ignore("Runs against a real API")
    @Test
    public void test() {
        EmployeeDB employeeDB = new EmployeeDB();
        List<Department> allDepartments = employeeDB.getAllDepartments();
        System.out.println(allDepartments);
        System.out.println(allDepartments.size());

       // List<Employee> allEmployees = employeeDB.getAllEmployees();
       // System.out.println(allEmployees);

       // List<Employee> magys = employeeDB.findEmploy("Magy");
       // System.out.println(magys);

//        List<Employee> salesEmployees = employeeDB.getEmployeesOfDepartment("Marketing");

//        System.out.println(salesEmployees.size());

//        System.out.println(salesEmployees);

    }
}
