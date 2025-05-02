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
    private final int employeeId; // static means share the same ID with all employees(I changed it and make it final since ID shouldn't change)
    private String firstName;
    private String lastName;
    private char gender;
    private String address;
    private int payLevel;
    private Department department;

    // add Id in the parameter
    public Employee(int employeeId,String firstName, String lastName, char gender, String address, int payLevel) {
        this.employeeId = employeeId;
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
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPayLevel(int payLevel) {
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
