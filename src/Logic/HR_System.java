package Logic;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class HR_System implements Serializable {
    private final ArrayList<Department> departments = new ArrayList<>(); 
    private final ArrayList<Employee> employees = new ArrayList<>();
    private int nextEmployeeId = 1;
    private int nextDepartmentId = 1;

    
    //Department Management
    //Req2: Add new department
    public void addDepartment(String name, String description, String location, double budget) {              
        Department dept = new Department(nextDepartmentId++, name, description, location, budget);
        System.out.println("Added department");
        departments.add(dept);
    }
    //Req3: Add new employee
    public void addEmployee(String firstName, String lastName, char gender, String address, int payLevel, String phoneNumber, String hireDate, String position) {
        Employee emp = new Employee(nextEmployeeId++, firstName, lastName, gender, address, payLevel, phoneNumber, hireDate, position);
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
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("hr_system.dat"))) {
            oos.writeObject(this);
        }
    }
    
    public static HR_System loadData() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("hr_system.dat"))) {
            return (HR_System) ois.readObject();
        }
    }


    //Req13: Initialize from file
    public void initializeFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Read number of departments
            int numDepartments = Integer.parseInt(scanner.nextLine());

            // Process each department
            for (int i = 0; i < numDepartments; i++) {
                // Read department details
                String deptName = scanner.nextLine();
                String deptLocation = scanner.nextLine();

                // Create the department with default values for description and budget
                // Using the existing addDepartment method
                String defaultDescription = "Department created from startup file";
                double defaultBudget = 100000.0;
                addDepartment(deptName, defaultDescription, deptLocation, defaultBudget);

                // Get the department we just created (last in the list)
                Department department = departments.get(departments.size() - 1);

                // Read number of employees in this department
                int numEmployees = Integer.parseInt(scanner.nextLine());

                // Process each employee
                for (int j = 0; j < numEmployees; j++) {
                    // Read employee details
                    String firstName = scanner.nextLine();
                    String lastName = scanner.nextLine();
                    char gender = scanner.nextLine().charAt(0);
                    String address = scanner.nextLine();
                    int salaryScale = Integer.parseInt(scanner.nextLine());

                    // Default values for fields not in the startup file
                    String defaultPhone = "000-0000";
                    String defaultHireDate = "01/01/2025";
                    String defaultPosition = "Staff";

                    // Create the employee using the existing addEmployee method
                    addEmployee(firstName, lastName, gender, address, salaryScale, 
                               defaultPhone, defaultHireDate, defaultPosition);

                    // Get the employee we just created (last in the list)
                    Employee employee = employees.get(employees.size() - 1);

                    // Assign the employee to the department
                    assignEmployeeToDepartment(employee.getEmployeeId(), department.getDepartmentId());
                }
            }

            scanner.close();
            System.out.println("Startup data loaded successfully.");

        } catch (FileNotFoundException e) {
            System.err.println("Startup file not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error loading startup data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    //Req14: Exit system with save
    public void exitSystem() {
        try {
            saveData();
            System.exit(0);
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

        // Create formatted strings for each pay level
        for (int i = 0; i < payScales.length; i++) {
            formattedPayLevels[i] = "Level " + (i + 1) + " - " + "BD" + payScales[i];
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
    
    public int getLastAddedEmployeeId() {
        // Return the ID of the last employee in the list
        if (employees.isEmpty()) {
            return -1; // No employees
        }
        return employees.get(employees.size() - 1).getEmployeeId();
    }
    
    public void generatePayrollReport() throws IOException {
        // 1. Open the file for writing
        try (PrintWriter writer = new PrintWriter(new FileWriter("payroll.txt"))) {
            double companyTotal = 0.0;

            writer.println("Payroll Report (Fortnightly)");
            writer.println("=".repeat(40));
            writer.println();

            for (Department dept : departments) {
                writer.println("Department: " + dept.getName());
                writer.println("-".repeat(40));
                double deptTotal = 0.0;

                ArrayList<Employee> deptEmployees = dept.getEmployees();
                if (deptEmployees.isEmpty()) {
                    writer.println("  No employees in this department.");
                } else {
                    for (Employee emp : deptEmployees) {
                        double annualSalary = getAnnualSalaryForPayLevel(emp.getPayLevel());
                        double fortnightlyPay = annualSalary / 26.0;
                        deptTotal += fortnightlyPay;

                        writer.printf("  %-20s (ID: %d)  Pay: BD%.2f%n",
                            emp.getFirstName() + " " + emp.getLastName(),
                            emp.getEmployeeId(),
                            fortnightlyPay
                        );
                    }
                }
                writer.printf("  Department Total: BD%.2f%n", deptTotal);
                writer.println();
                companyTotal += deptTotal;
            }

            writer.println("=".repeat(40));
            writer.printf("Company Total Payroll This Fortnight: BD%.2f%n", companyTotal);
        }
    }

    // Helper method to get annual salary for a pay level
    public double getAnnualSalaryForPayLevel(int payLevel) {
        double[] payScales = {
            44245.75, // Level 1
            48670.32, // Level 2
            53537.35, // Level 3
            58891.09, // Level 4
            64780.20, // Level 5
            71258.22, // Level 6
            80946.95, // Level 7
            96336.34  // Level 8
        };
        if (payLevel >= 1 && payLevel <= payScales.length) {
            return payScales[payLevel - 1];
        } else {
            return 0.0; // Or throw an exception if invalid
        }
    }
    
}
