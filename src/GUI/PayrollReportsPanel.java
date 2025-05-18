/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Logic.HR_System;
import Logic.SystemManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pvppl
 */
public class PayrollReportsPanel extends javax.swing.JPanel {
    private HR_System system;
    private JTable payrollTable;
    private DefaultTableModel payrollTableModel;
    private JScrollPane tableScrollPane;
    private double totalFortnightlyPayroll = 0.0;

    /**
     * Creates new form PayrollReportsPanel
     */
    public PayrollReportsPanel() {
        this.system = SystemManager.getInstance().getSystem();
    
        // Initialize the table and model with department column
        payrollTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Employee ID", "Name", "Department", "Pay Level", "Fortnightly Pay"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        payrollTable = new JTable(payrollTableModel);
        tableScrollPane = new JScrollPane(payrollTable);

        initComponents();

        // Remove the left margin in the layout
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) getLayout();
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0) // Changed from original value to 0
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(employeeTitle1)
                        .addGap(928, 928, 928)
                        .addComponent(btnDownloadReport, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        // IMPORTANT: Replace the entire content of jPanel1 with a simple layout
        jPanel1.removeAll();
        jPanel1.setLayout(new BorderLayout());

        // Add a header panel at the top
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(employeeTitle, BorderLayout.NORTH);
        headerPanel.add(jLabel1, BorderLayout.CENTER);

        // Add department selector to the header
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(comboDepartments, BorderLayout.EAST);
        headerPanel.add(filterPanel, BorderLayout.EAST);

        jPanel1.add(headerPanel, BorderLayout.NORTH);

        // Add the table scroll pane to the center
        jPanel1.add(tableScrollPane, BorderLayout.CENTER);

        // Set up the table and populate it
        setupPayrollTable();
        populatePayrollTable();

        // Update the total label
        updateTotalLabel();

        // Force the UI to update
        jPanel1.revalidate();
        jPanel1.repaint();

        // Add action listener to the department combo box
        comboDepartments.addActionListener(e -> {
            String selected = (String) comboDepartments.getSelectedItem();
            if (selected != null) {
                if (selected.equals("All Departments")) {
                    populatePayrollTable();
                } else {
                    populatePayrollTableForDepartment(selected);
                }
                updateTotalLabel();
            }
        });
        
    }
    
    private void setupPayrollTable() {
        // Make sure the table is visible
        payrollTable.setVisible(true);
        tableScrollPane.setVisible(true);

        // Set preferred size for the table scroll pane - REDUCE HEIGHT HERE
        tableScrollPane.setPreferredSize(new Dimension(1100, 300)); // Changed from 400 to 300

        // Set column widths
        payrollTable.getColumnModel().getColumn(0).setPreferredWidth(100);  // Employee ID
        payrollTable.getColumnModel().getColumn(1).setPreferredWidth(200);  // Name
        payrollTable.getColumnModel().getColumn(2).setPreferredWidth(150);  // Department
        payrollTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Pay Level
        payrollTable.getColumnModel().getColumn(4).setPreferredWidth(150);  // Fortnightly Pay

        // Prevent column resizing by the user to maintain our custom widths
        payrollTable.getTableHeader().setResizingAllowed(false);

        // Set row height - REDUCE ROW HEIGHT
        payrollTable.setRowHeight(25); // Changed from 30 to 25

        // Force the table to repaint
        payrollTable.revalidate();
        payrollTable.repaint();
    }
    
    private void populatePayrollTable() {
        payrollTableModel.setRowCount(0); // Clear previous data
        totalFortnightlyPayroll = 0.0;

        HR_System system = SystemManager.getInstance().getSystem();

        for (var dept : system.listDepartments()) {
            for (var emp : dept.getEmployees()) {
                int payLevel = emp.getPayLevel();
                double annualSalary = system.getAnnualSalaryForPayLevel(payLevel);
                double fortnightlyPay = annualSalary / 26.0;
                totalFortnightlyPayroll += fortnightlyPay;

                payrollTableModel.addRow(new Object[]{
                    emp.getEmployeeId(),
                    emp.getFirstName() + " " + emp.getLastName(),
                    dept.getName(),
                    payLevel,
                    String.format("BD%.2f", fortnightlyPay)
                });
            }
        }

        // Force the table to repaint
        forceTableRefresh();
    }
   
    
    private void updatePayrollTable(String departmentName) {
        if (departmentName == null) {
            // If departmentName is null, just populate with all departments
            populatePayrollTable();
            return;
        }

        if (departmentName.equals("All Departments")) {
            populatePayrollTable();
        } else {
            populatePayrollTableForDepartment(departmentName);
        }
    }
    
    private void populatePayrollTableForDepartment(String departmentName) {
        payrollTableModel.setRowCount(0); // Clear previous data
        totalFortnightlyPayroll = 0.0;

        HR_System system = SystemManager.getInstance().getSystem();

        for (var dept : system.listDepartments()) {
            if (dept.getName().equals(departmentName)) {
                for (var emp : dept.getEmployees()) {
                    int payLevel = emp.getPayLevel();
                    double annualSalary = system.getAnnualSalaryForPayLevel(payLevel);
                    double fortnightlyPay = annualSalary / 26.0;
                    totalFortnightlyPayroll += fortnightlyPay;

                    payrollTableModel.addRow(new Object[]{
                        emp.getEmployeeId(),
                        emp.getFirstName() + " " + emp.getLastName(),
                        dept.getName(),
                        payLevel,
                        String.format("BD%.2f", fortnightlyPay)
                    });
                }
                break;
            }
        }

        // Force the table to repaint
        forceTableRefresh();
    }
    
    private void forceTableRefresh() {
        // Force the table to repaint
        payrollTableModel.fireTableDataChanged();
        payrollTable.revalidate();
        payrollTable.repaint();
        
        // Force the container to repaint
        tableScrollPane.revalidate();
        tableScrollPane.repaint();
        jPanel1.revalidate();
        jPanel1.repaint();
        this.revalidate();
        this.repaint();
    }
    
    private void updateTotalLabel() {
        jLabel8.setText(String.format("BD%.2f", totalFortnightlyPayroll));
    }
    
    public void refreshDepartmentsComboBox() {
        comboDepartments.removeAllItems();
        comboDepartments.addItem("All Departments");
        for (var dept : SystemManager.getInstance().getSystem().listDepartments()) {
            comboDepartments.addItem(dept.getName());
        }
    }

    private void downloadReport() {
        try {
            // Create a file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Payroll Report");
            fileChooser.setSelectedFile(new File("payroll.txt"));

            // Show save dialog
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                // Ensure the file has .txt extension
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".txt")) {
                    filePath += ".txt";
                    fileToSave = new File(filePath);
                }

                // Create a PrintWriter to write to the file
                try (PrintWriter writer = new PrintWriter(fileToSave)) {
                    // Write header
                    writer.println("FORTNIGHTLY PAYROLL REPORT");
                    writer.println("=========================");
                    writer.println();

                    // Get the selected department
                    String selectedDept = (String) comboDepartments.getSelectedItem();
                    if (selectedDept != null && !selectedDept.equals("All Departments")) {
                        writer.println("Department: " + selectedDept);
                    } else {
                        writer.println("All Departments");
                    }
                    writer.println();

                    // Write column headers
                    writer.println(String.format("%-10s %-30s %-15s", "ID", "Name", "Fortnightly Pay"));
                    writer.println(String.format("%-10s %-30s %-15s", "----------", "------------------------------", "---------------"));

                    // Write data for each employee
                    HR_System system = SystemManager.getInstance().getSystem();

                    if (selectedDept != null && !selectedDept.equals("All Departments")) {
                        // Write data for the selected department
                        for (var dept : system.listDepartments()) {
                            if (dept.getName().equals(selectedDept)) {
                                for (var emp : dept.getEmployees()) {
                                    int payLevel = emp.getPayLevel();
                                    double annualSalary = system.getAnnualSalaryForPayLevel(payLevel);
                                    double fortnightlyPay = annualSalary / 26.0;

                                    writer.println(String.format("%-10s %-30s BD%-13.2f", 
                                        emp.getEmployeeId(),
                                        emp.getFirstName() + " " + emp.getLastName(),
                                        fortnightlyPay));
                                }
                                break;
                            }
                        }
                    } else {
                        // Write data for all departments
                        for (var dept : system.listDepartments()) {
                            writer.println();
                            writer.println("Department: " + dept.getName());
                            writer.println();

                            for (var emp : dept.getEmployees()) {
                                int payLevel = emp.getPayLevel();
                                double annualSalary = system.getAnnualSalaryForPayLevel(payLevel);
                                double fortnightlyPay = annualSalary / 26.0;

                                writer.println(String.format("%-10s %-30s BD%-13.2f", 
                                    emp.getEmployeeId(),
                                    emp.getFirstName() + " " + emp.getLastName(),
                                    fortnightlyPay));
                            }
                        }
                    }

                    // Write total
                    writer.println();
                    writer.println(String.format("%-41s BD%-13.2f", "Total Fortnightly Payroll:", totalFortnightlyPayroll));

                    JOptionPane.showMessageDialog(this, 
                        "Report successfully saved to: " + fileToSave.getPath(), 
                        "Export Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error saving report: " + e.getMessage(), 
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDownloadReport = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        employeeTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        comboDepartments = new javax.swing.JComboBox<>();
        employeeTitle1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        btnDownloadReport.setBackground(new java.awt.Color(67, 97, 238));
        btnDownloadReport.setForeground(new java.awt.Color(255, 255, 255));
        btnDownloadReport.setText("Download Report");
        btnDownloadReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadReportActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(238, 238, 238));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        employeeTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        employeeTitle.setForeground(new java.awt.Color(67, 97, 238));
        employeeTitle.setText("Fortnightly Payroll Report");

        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Generate and view payroll reports for all employees or by department.   ");

        comboDepartments.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(employeeTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 672, Short.MAX_VALUE)
                        .addComponent(comboDepartments, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboDepartments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(289, Short.MAX_VALUE))
        );

        employeeTitle1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        employeeTitle1.setText("PayRoll Reports");

        jPanel3.setBackground(new java.awt.Color(232, 232, 232));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Total Fortnighlty Payroll");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("$40.000");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addGap(49, 49, 49))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(employeeTitle1)
                        .addGap(928, 928, 928)
                        .addComponent(btnDownloadReport, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeTitle1)
                    .addComponent(btnDownloadReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDownloadReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadReportActionPerformed
        // TODO add your handling code here:
        downloadReport();
        
    }//GEN-LAST:event_btnDownloadReportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDownloadReport;
    private javax.swing.JComboBox<String> comboDepartments;
    private javax.swing.JLabel employeeTitle;
    private javax.swing.JLabel employeeTitle1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
