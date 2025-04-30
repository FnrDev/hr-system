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

 //rename the class to HrSystem to avoid conflict with java.lang.System
public class HrSystem {
 
    private PayReport payreport;
    private ArrayList<Department> departments = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();
    private int nextEmployeeId = 1;
    private int nextDepartmentId = 1;

    public void addDepartment(Department department) {
        if (department == null || department.getName() == null || department.getLocation() == null) {
            throw new IllegalArgumentException("Invalid department details.");
        }

        department.setDepartmentId(nextDepartmentId++);
        departments.add(department);
    }

    public void addEmployee(Employee employee) {
        if (employee == null || employee.getFirstName() == null || employee.getLastName() == null
                || !(employee.getGender() == 'M' || employee.getGender() == 'F')
                || employee.getAddress() == null || employee.getPayLevel() < 1 || employee.getPayLevel() > 8) {
            throw new IllegalArgumentException("Invalid employee details.");
        }

        employee.setEmployeeId(nextEmployeeId++);
        employees.add(employee);
    }


    
    public Department updateDepartment(Department department) {
        
    }
    
    public Employee updateEmployee(Employee employee) {
        
    }
    
    public void removeDepartment() {
        
    }
    
    public void removeEmployee() {
        
    }
    
    public void assignEmployeeToDepartment(Employee employee, Department department) {
        
    }
    
    public void setDepartmentHead(Employee employee, Department department) {
        
    }
    
    public ArrayList<Employee> listDepartmentEmployees(Department department) {
        
    }
    
    public Employee searchEmployeeById(int employeeId) {
        
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
