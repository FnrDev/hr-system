/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;
import java.io.Serializable;
/**
 *
 * @author MY PC
 */
public class Employee implements Serializable {
    private int employeeId; 
    private String firstName;
    private String lastName;
    private char gender;
    private String address;
    private int payLevel;
    private Department department;

    // add Id in the parameter
    public Employee(String firstName, String lastName, char gender, String address, int payLevel) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (gender != 'M' && gender != 'F') {
            throw new IllegalArgumentException("Gender must be 'M' or 'F'");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        if (payLevel < 1 || payLevel > 8) {
            throw new IllegalArgumentException("Pay level must be between 1 and 8");
        }
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.payLevel = payLevel;
    }

    //Getters
    public int getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public int getPayLevel() {
        return payLevel;
    }

    public Department getDepartment() {
        return department;
    }
    
    //Setters
    public void setEmployeeId(int id) {
        this.employeeId = id;
    }
    
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.lastName = lastName;
    }

    public void setGender(char gender) {
        if (gender != 'M' && gender != 'F') {
            throw new IllegalArgumentException("Gender must be 'M' or 'F'");
        }
        this.gender = gender;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        this.address = address;
    }

    public void setPayLevel(int payLevel) {
        if (payLevel < 1 || payLevel > 8) {
            throw new IllegalArgumentException("Pay level must be between 1 and 8");
        }
        this.payLevel = payLevel;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    //Salary Calculation Methods
    public double calculateAnnualSalary(){
        double[] payScales = {
            44245.75, //Level 1
            48670.32, //Level 2
            53537.35, //Level 3
            58891.09, //Level 4
            64780.20, //Level 5
            71258.22, //Level 6
            80946.95, //Level 7
            96336.34  //Level 8
        };
        return payScales[payLevel - 1]; //Get the salary for employee's level
    }
    public double calculateFortnightlyPay(){
        return calculateAnnualSalary() / 26;
    }
    
    
}
