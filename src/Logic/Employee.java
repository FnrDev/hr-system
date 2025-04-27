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

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

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
    
    
}
