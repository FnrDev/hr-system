package GUI;

import Logic.Department;
import Logic.Employee;
import Logic.HR_System;
import Logic.SystemManager;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DepartmentEmployeesDialog extends javax.swing.JDialog {
    private Department department;
    private DefaultTableModel tableModel;
    private JTable employeeTable;
    private JScrollPane scrollPane;
    private HR_System system;
    private DepartmentsPanel parentPanel;
    
    // UI Components
    private JLabel headerLabel;
    private JLabel countLabel;
    private JButton setAsHeadButton;
    private JButton removeHeadButton;
    private JButton closeButton;
    
    /**
     * Creates new dialog
     */
    public DepartmentEmployeesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.system = SystemManager.getInstance().getSystem();
        
        // Initialize UI components first
        initComponents();
        
        // Then set up our custom components
        setupCustomComponents();
    }
    
    /**
     * Set up custom UI components beyond what NetBeans generated
     */
    private void setupCustomComponents() {
        // Create UI components
        headerLabel = new javax.swing.JLabel();
        countLabel = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        
        // Set up table model
        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Name", "Position", "Pay Level", "Is Head"}
        ) {
            Class<?>[] columnTypes = new Class<?>[] {
                Integer.class, String.class, String.class, Integer.class, String.class
            };
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        };
        
        // Create and configure table
        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        
        // Set column widths
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Position
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Pay Level
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Is Head
        
        // Add selection listener to update button states
        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates();
            }
        });
        
        scrollPane.setViewportView(employeeTable);
        
        // Create action buttons
        setAsHeadButton = new javax.swing.JButton("Set as Head");
        setAsHeadButton.setBackground(new java.awt.Color(67, 97, 238));
        setAsHeadButton.setForeground(java.awt.Color.WHITE);
        setAsHeadButton.addActionListener(this::setAsHeadButtonActionPerformed);
        setAsHeadButton.setEnabled(false); // Initially disabled until selection
        
        removeHeadButton = new javax.swing.JButton("Remove Head");
        removeHeadButton.setBackground(new java.awt.Color(220, 53, 69));
        removeHeadButton.setForeground(java.awt.Color.WHITE);
        removeHeadButton.addActionListener(this::removeHeadButtonActionPerformed);
        removeHeadButton.setEnabled(false); // Initially disabled until there's a head
        
        closeButton = new javax.swing.JButton("Close");
        closeButton.addActionListener(evt -> dispose());
        
        // Configure header label
        headerLabel.setFont(new java.awt.Font("Segoe UI", 1, 18));
        headerLabel.setText("Department Employees");
        
        // Configure count label
        countLabel.setText("Total: 0 employees");
        
        // Set up layout
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(countLabel, BorderLayout.EAST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(setAsHeadButton);
        buttonPanel.add(removeHeadButton);
        buttonPanel.add(closeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Clear out and replace the content pane layout
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        // Dialog properties
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Department Employees");
        setModal(true);
        setSize(new java.awt.Dimension(700, 500));
        setLocationRelativeTo(null);
    }
    
    /**
     * Set the department to display
     */
    public void setDepartment(Department department) {
        this.department = department;
        headerLabel.setText("Employees of " + department.getName());
        loadEmployees();
    }
    
    /**
     * Set reference to parent panel for refreshing
     */
    public void setParentPanel(DepartmentsPanel parentPanel) {
        this.parentPanel = parentPanel;
    }
    
    /**
     * Load employees from department into the table
     */
    private void loadEmployees() {
        // Clear existing rows
        tableModel.setRowCount(0);
        
        // Get employees from department
        ArrayList<Employee> employees = department.getEmployees();
        
        // Add employees to table
        for (Employee emp : employees) {
            boolean isHead = department.getHeadOfDepartment() != null && 
                            department.getHeadOfDepartment().getEmployeeId() == emp.getEmployeeId();
            
            tableModel.addRow(new Object[]{
                emp.getEmployeeId(),
                emp.getFirstName() + " " + emp.getLastName(),
                emp.getPosition(),
                emp.getPayLevel(),
                isHead ? "Yes" : "No"
            });
        }
        
        // Update header count
        countLabel.setText("Total: " + employees.size() + " employees");
        
        // Update button states
        updateButtonStates();
    }
    
    /**
     * Update button enabled states based on current selection and department head
     */
    private void updateButtonStates() {
        // Enable/disable remove head button based on whether there is a head
        removeHeadButton.setEnabled(department != null && department.getHeadOfDepartment() != null);
        
        // Enable/disable set as head button based on table selection
        boolean hasSelection = employeeTable.getSelectedRow() != -1;
        setAsHeadButton.setEnabled(hasSelection);
    }
    
    /**
     * Handle Set as Head button click
     */
    private void setAsHeadButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an employee first.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get employee ID from table
        int employeeId = (int) tableModel.getValueAt(selectedRow, 0);
        
        // Find employee in department
        for (Employee emp : department.getEmployees()) {
            if (emp.getEmployeeId() == employeeId) {
                setAsHead(emp);
                break;
            }
        }
    }
    
    /**
     * Handle Remove Head button click
     */
    private void removeHeadButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (department.getHeadOfDepartment() == null) {
            JOptionPane.showMessageDialog(this, 
                "This department does not have a head to remove.", 
                "No Head", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        removeAsHead(department.getHeadOfDepartment());
    }
    
    /**
     * Set an employee as department head
     */
    private void setAsHead(Employee emp) {
        try {
            // If there's already a head, confirm replacement
            if (department.getHeadOfDepartment() != null) {
                // Check if this employee is already the head
                if (department.getHeadOfDepartment().getEmployeeId() == emp.getEmployeeId()) {
                    JOptionPane.showMessageDialog(this, 
                        emp.getFirstName() + " " + emp.getLastName() + 
                        " is already the Head of " + department.getName(),
                        "Already Head",
                        JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                String currentHead = department.getHeadOfDepartment().getFirstName() + " " + 
                                     department.getHeadOfDepartment().getLastName();
                String newHead = emp.getFirstName() + " " + emp.getLastName();
                
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Replace current head (" + currentHead + ") with " + newHead + "?",
                    "Confirm Head Change",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
            // Set employee as department head
            department.setHeadOfDepartment(emp);
            JOptionPane.showMessageDialog(this, 
                emp.getFirstName() + " " + emp.getLastName() + " is now the Head of " + department.getName());
            
            // Refresh table
            loadEmployees();
            
            // Notify parent panel to refresh
            if (parentPanel != null) {
                parentPanel.refreshDepartments();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error setting department head: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Remove an employee as department head
     */
    private void removeAsHead(Employee emp) {
        try {
            // Confirm removal
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Remove " + emp.getFirstName() + " " + emp.getLastName() + 
                " as Head of " + department.getName() + "?",
                "Confirm Head Removal",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            
            // Remove employee as department head
            department.setHeadOfDepartment(null);
            JOptionPane.showMessageDialog(this, 
                "Removed " + emp.getFirstName() + " " + emp.getLastName() + 
                " as Head of " + department.getName());
            
            // Refresh table
            loadEmployees();
            
            // Notify parent panel to refresh
            if (parentPanel != null) {
                parentPanel.refreshDepartments();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error removing department head: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DepartmentEmployeesDialog dialog = new DepartmentEmployeesDialog(new javax.swing.JFrame(), true);
            
            try {
                // Create test department
                Department testDepartment = new Department(1, "Test Department", "For testing", "Test Location", 500000.0);
                
                // Add test employees with proper IDs
                Employee emp1 = new Employee(101, "John", "Doe", 'M', "123 Test St", 5, "555-1234", "2022-01-01", "Developer");
                Employee emp2 = new Employee(102, "Jane", "Smith", 'F', "456 Sample Ave", 6, "555-5678", "2021-05-15", "Designer");
                Employee emp3 = new Employee(103, "Bob", "Johnson", 'M', "789 Demo Rd", 4, "555-9012", "2023-03-10", "Tester");
                
                testDepartment.addEmployee(emp1);
                testDepartment.addEmployee(emp2);
                testDepartment.addEmployee(emp3);
                
                // Set department for dialog
                dialog.setDepartment(testDepartment);
                dialog.setVisible(true);
            } catch (Exception e) {
                System.err.println("Error creating test data: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
