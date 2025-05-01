/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

/**
 *
 * @author MY PC
 */
public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private char gender;
    private String address;
    private int payLevel;
    private Department department;

    // Remove employeeId from constructor
    public Employee(String firstName, String lastName, char gender, String address, int payLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.payLevel = payLevel;
    }

    // Add setter for employeeId
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
