package Logic;
import java.util.ArrayList;
import java.io.*;

public class HR_System implements Serializable {
    private final ArrayList<Department> departments = new ArrayList<>(); 
    private final ArrayList<Employee> employees = new ArrayList<>();
    private int nextEmployeeId = 1;
    private int nextDepartmentId = 1;

    
    //Department Management
    //Req2: Add new department
    public void addDepartment(String name, String location, double budget) {              
        Department dept = new Department(nextDepartmentId++, name, location, budget);
        System.out.println("Added department");
        departments.add(dept);
    }
    //Req3: Add new employee
    public void addEmployee(String firstName, String lastName, char gender, String address, int payLevel) {
        Employee emp = new Employee(firstName, lastName, gender, address, payLevel);
        emp.setEmployeeId(nextEmployeeId++);
        employees.add(emp); // 
    }
    //Req4: Update employee details
    public void updateEmployee(int employeeId, String firstName, String lastName, char gender, String address, int payLevel) {
        Employee emp = getEmployeeById(employeeId);
        if(emp != null) {
            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            emp.setGender(gender);
            emp.setAddress(address);
            emp.setPayLevel(payLevel);
        }
    }
    //Req5: Update department details
    public void updateDepartment(int departmentId, String name, String location) {
        Department dept = getDepartmentById(departmentId);
        if(dept != null) {
            dept.setName(name);
            dept.setLocation(location);
        }
    }
    //Req6: Assign employee to department
    public void assignEmployeeToDepartment(int employeeId, int departmentId) {
        Employee emp = getEmployeeById(employeeId);
        Department dept = getDepartmentById(departmentId);
        
        if(emp != null && dept != null) {                 // Remove from current department
            Department currentDept = emp.getDepartment(); 
            if(currentDept != null) {
                currentDept.removeEmployee(emp);
            }
            
            dept.addEmployee(emp);
            emp.setDepartment(dept);
        }
    }
    //Req7: set department head
    public void setDepartmentHead(int departmentId, int employeeId) {
        Department dept = getDepartmentById(departmentId);
        Employee emp = getEmployeeById(employeeId);
        
        if(dept != null && emp != null && dept.getEmployees().contains(emp)) {
            dept.setHeadOfDepartment(emp);
        }
    }
    //Req8: Delete department
    public void deleteDepartment(int departmentId) {
        Department dept = getDepartmentById(departmentId);
        if(dept != null && dept.getEmployeeCount() == 0) {
            departments.remove(dept);
        }
    }
    //Req9: Delete employee
    public void deleteEmployee(int employeeId) {
        Employee emp = getEmployeeById(employeeId);
        if(emp != null) {
            Department dept = emp.getDepartment();
            if(dept != null) {
                dept.removeEmployee(emp);
                if(dept.getHeadOfDepartment() != null && 
                   dept.getHeadOfDepartment().getEmployeeId() == employeeId) {
                    dept.setHeadOfDepartment(null);
                }
            }
            employees.remove(emp);
        }
    }
    //Req10: List department employees
    public ArrayList<Employee> listDepartmentEmployees(Department department) {
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
    
    //Req11: Generate pay report
    
    //Req12: Load system state
    public void saveData() throws IOException {
        //code here
    }
    public void loadData() throws IOException {
        //code here    
    }

    //Req13: Initialize from file
    public void initializeFromFile(String filePath) {
        //code here
    }
    
    //Req14: Exit system with save
    public void exitSystem() {
        try {
            saveData();
        } catch(IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    
    public String[] getPayLevels() {
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

        // Create a string array of the same length
        String[] formattedPayLevels = new String[payScales.length];

        // Format currency with commas and 2 decimal places
        java.text.NumberFormat currencyFormat = java.text.NumberFormat.getCurrencyInstance();

        // Create formatted strings for each pay level
        for (int i = 0; i < payScales.length; i++) {
            formattedPayLevels[i] = "Level " + (i + 1) + " - " + currencyFormat.format(payScales[i]);
        }

        return formattedPayLevels;
    }
    
    public ArrayList<Department> listDepartments() {
        return departments;
    }
    
    public ArrayList<Employee> listEmployees() {
        return employees;
    }

    
    // Helper methods
    private Employee getEmployeeById(int id) {
        return employees.stream()
                .filter(e -> e.getEmployeeId() == id)
                .findFirst()
                .orElse(null);
    }

    private Department getDepartmentById(int id) {
        return departments.stream()
                .filter(d -> d.getDepartmentId() == id)
                .findFirst()
                .orElse(null);
    }
    
    // Add this to your HR_System class
    public int getLastAddedEmployeeId() {
        // Return the ID of the last employee in the list
        if (employees.isEmpty()) {
            return -1; // No employees
        }
        return employees.get(employees.size() - 1).getEmployeeId();
    }
}
