/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.util.ArrayList;
import java.io.Serializable;

/**
 *
 * @author MY PC
 */
public class Department implements Serializable {
    private final int departmentId;
    private String name;
    private String location;
    private final ArrayList<Employee> employees; //
    private Employee headOfDepartment;
    private double budget;

    // Constractor
    public Department(int departmentId, String name, String location, double budget) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }

        this.departmentId = departmentId;
        this.name = name;
        this.location = location;
        this.employees = new ArrayList<>();
        this.headOfDepartment = null; 
        this.budget = budget;
    }
    
    //Getters
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
        return new ArrayList<>(employees); //
    }

    public Employee getHeadOfDepartment() {
        return headOfDepartment;
    }

    //Setters
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty");
        }
        this.name = name;
    }

    public void setLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        this.location = location;
    }

    //department operations
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new NullPointerException("Employee cannot be null");
        }
        if (!employees.contains(employee)) {
            employees.add(employee);
            employee.setDepartment(this);
        }
    }

    public void removeEmployee(Employee employee) {
        if (employee == null) {
            throw new NullPointerException("Employee cannot be null");
        }
        if (employees.remove(employee)) {
            employee.setDepartment(null); 
            
            //clear head position
            if (employee.equals(headOfDepartment)) {
                headOfDepartment = null;
            }
        }
    }

    public void setHeadOfDepartment(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            throw new IllegalArgumentException("Head must be a department employee");
        }
        this.headOfDepartment = employee;
    }

    //Business operations
    public int getEmployeeCount() {
        return employees.size();
    }
    
    public double getTotalDepartmentPay() {
        double total = 0.0;
        for (Employee emp : employees) {
            total += emp.calculateAnnualSalary();
        }
        return total;
    }
}
