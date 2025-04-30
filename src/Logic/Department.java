/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.util.ArrayList;

/**
 *
 * @author MY PC
 */
public class Department {
    private int departmentId;
    private String name;
    private String location;
    private ArrayList<Employee> employees;
    private Employee headOfDepartment;

    // Add no-arg constructor for flexibility (if not already present)
public Department() {
    this.employees = new ArrayList<>();
}

// Add constructor without ID
public Department(String name, String location) {
    this.name = name;
    this.location = location;
    this.employees = new ArrayList<>();
}


    public int getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public Employee getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void setHeadOfDepartment(Employee headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }
    
    
}
