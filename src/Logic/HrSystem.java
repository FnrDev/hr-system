/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;
import java.util.ArrayList;
import java.io.*;

/**
 *
 * @author MY PC
 */

 //rename the class to HrSystem to avoid conflict with java.lang.System
public class HrSystem implements Serializable{
    private PayReport payreport;
    private ArrayList<Department> departments = new ArrayList<>(); 
    private ArrayList<Employee> employees = new ArrayList<>();
    private int nextEmployeeId = 1;
    private int nextDepartmentId = 1;

    
    //Department Management
    public void addDepartment(String name, String location) {
        Department dept = new Department(name, location);
        dept.setDepartmentId(nextDepartmentId++); //
        departments.add(dept);
        }

    public void removeDepartment(int departmentId) {
        Department dept = findDepartmentById(departmentId);
        if (dept != null && dept.getEmployeeCount() == 0) {
            departments.remove(dept);
        }
    }

    public void addEmployee(String firstName, String lastName, char gender, String address, int payLevel) {
        Employee emp = new Employee(firstName, lastName, gender, address, payLevel);
        emp.setEmployeeId(nextEmployeeId++);
        employees.add(emp); //
        
    }
    
    public Department updateDepartment(Department updated) {
        if (updated == null || updated.getDepartmentId() <= 0) {
            throw new IllegalArgumentException("Invalid department.");
        }
    
        for (Department d : departments) {
            if (d.getDepartmentId() == updated.getDepartmentId()) {
                d.setName(updated.getName());
                d.setLocation(updated.getLocation());
                return d;
            }
        }
    
        return null; // Not found
    }
    
    
    public Employee updateEmployee(Employee updated) {
        if (updated == null || updated.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Invalid employee.");
        }
    
        for (Employee e : employees) {
            if (e.getEmployeeId() == updated.getEmployeeId()) {
                e.setFirstName(updated.getFirstName());
                e.setLastName(updated.getLastName());
                e.setGender(updated.getGender());
                e.setAddress(updated.getAddress());
                e.setPayLevel(updated.getPayLevel());
                // Department is not changed here; use assignEmployeeToDepartment for that
                return e;
            }
        }
    
        return null; // Not found
    }
    
    
   //Req-6
    public void assignEmployeeToDepartment(Employee employee, Department newDepartment) {
        if (employee == null || newDepartment == null) {
            throw new IllegalArgumentException("Employee or Department is null.");
        }
    
        // Prevent assigning if employee is head of a department
        for (Department d : departments) {
            if (d.getHeadOfDepartment() != null &&
                d.getHeadOfDepartment().getEmployeeId() == employee.getEmployeeId()) {
                throw new IllegalStateException("Cannot reassign employee who is a department head. Remove head role first.");
            }
        }
    
        // Remove employee from current department, if any
        Department currentDept = employee.getDepartment();
        if (currentDept != null) {
            currentDept.getEmployees().remove(employee);
        }
    
        // Assign employee to new department
        employee.setDepartment(newDepartment);
        newDepartment.getEmployees().add(employee);
    }
    
    //Req-7
    public void setDepartmentHead(Employee employee, Department department) {
        if (employee == null || department == null) {
            throw new IllegalArgumentException("Employee or Department is null.");
        }
    
        // Check if the employee is part of the department
        if (!department.getEmployees().contains(employee)) {
            throw new IllegalStateException("Employee must be part of the department to be designated as head.");
        }
    
        // Assign head
        department.setHeadOfDepartment(employee);
    }
    
    //Req-10
    public ArrayList<Employee> listDepartmentEmployees(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department is null.");
        }
    
        return department.getEmployees();
    }
    
    
    public Employee searchEmployeeById(int employeeId) {
        for (Employee e : employees) {
            if (e.getEmployeeId() == employeeId) {
                return e;
            }
        }
        return null; // Not found
    }
    
    
    public void removeEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee is null.");
        }
    
        Department dept = employee.getDepartment();
        if (dept != null) {
            // If head of department, remove role first
            if (dept.getHeadOfDepartment() != null &&
                dept.getHeadOfDepartment().getEmployeeId() == employee.getEmployeeId()) {
                dept.setHeadOfDepartment(null);
            }
    
            // Remove from department’s list
            dept.getEmployees().remove(employee);
        }
    
        // Remove from main system list
        employees.removeIf(e -> e.getEmployeeId() == employee.getEmployeeId());
    }
    
    
    public void exitSystem() {
        
    }
    
    public void saveData() {
        
    }
    
    public void loadData() {
        
    }
    
    public void initializeFromFile(String filePath) {
        
    }
    
    public void getReportDetails(PayReport payReport) {
        
    }
}
