/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI;

import Logic.Department;
import Logic.SystemManager;
import Logic.Employee;
import Logic.HR_System;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author pvppl
 */
public class AddEmployeeDialog extends javax.swing.JDialog {
    private HR_System system;
    private Employee employeeToEdit; // Add this field
    private boolean isEditMode; // Add this field to track if we're editing or adding

    /**
     * Creates new form AddEmployeeDialog
     */
    public AddEmployeeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.system = SystemManager.getInstance().getSystem();
        initComponents();
        
        // Initialize combo boxes with data
        initializeComboBoxes();
        
        // Set dialog title for adding
        setTitle("Add New Employee");
        
        // Center the dialog on screen
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                initializeComboBoxes();
            }
        });
    }
    
    public AddEmployeeDialog(java.awt.Frame parent, boolean modal, Employee employeeToEdit) {
        super(parent, modal);
        this.system = SystemManager.getInstance().getSystem();
        this.isEditMode = true; // We are in edit mode
        this.employeeToEdit = employeeToEdit;

        initComponents();

        // Set dialog title for editing
        setTitle("Edit Employee");

        // Update text for editing
        btnSaveEmployee.setText("Update Employee");
        lblTitle.setText("Update existing employee");
        lblDescription.setText("Update employee record in the system");

        // IMPORTANT: Initialize combo boxes BEFORE populating fields
        initializeComboBoxes();

        // THEN populate fields with employee data
        populateFieldsWithEmployeeData();

        // Center the dialog on screen
        setLocationRelativeTo(null);
    }
    
    // Add this method to populate fields with employee data
    private void populateFieldsWithEmployeeData() {
        if (employeeToEdit != null) {
            // Populate text fields
            firstNameInput.setText(employeeToEdit.getFirstName());
            lastNameInput.setText(employeeToEdit.getLastName());
            poistionInput.setText(employeeToEdit.getPosition());
            txtHireDate.setText(employeeToEdit.getHireDate());
            txtPhone.setText(employeeToEdit.getPhoneNumber());
            addressInput.setText(employeeToEdit.getAddress());

            // Set gender selection in combo box
            if (employeeToEdit.getGender() == 'M') {
                genderBOX.setSelectedItem("Male");
            } else if (employeeToEdit.getGender() == 'F') {
                genderBOX.setSelectedItem("Female");
            }

            // Set department selection in combo box
            if (employeeToEdit != null) {
            // Other field population code...

                // DIRECT DEPARTMENT SELECTION APPROACH
                if (employeeToEdit.getDepartment() != null) {
                    String targetDeptName = employeeToEdit.getDepartment().getName();
                    System.out.println("Attempting to set department to: " + targetDeptName);

                    // Force selection by directly setting the model's selected item
                    DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) departmentBOX.getModel();

                    // First try: Direct set by value
                    model.setSelectedItem(targetDeptName);

                    // Check if it worked
                    if (!targetDeptName.equals(departmentBOX.getSelectedItem())) {
                        System.out.println("First attempt failed. Current selection: " + departmentBOX.getSelectedItem());

                        // Second try: Find exact match and select by index
                        for (int i = 0; i < model.getSize(); i++) {
                            String item = model.getElementAt(i);
                            if (targetDeptName.equals(item)) {
                                departmentBOX.setSelectedIndex(i);
                                System.out.println("Found exact match at index " + i);
                                break;
                            }
                        }
                    }

                    // Final check
                    System.out.println("Final department selection: " + departmentBOX.getSelectedItem());
                }

                // Set pay level in combo box
                for (int i = 0; i < paylevelBOX.getItemCount(); i++) {
                    String item = paylevelBOX.getItemAt(i).toString();
                    if (item.startsWith("Level " + employeeToEdit.getPayLevel())) {
                        paylevelBOX.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Initialize combo boxes with dynamic data
     */
    private void initializeComboBoxes() {
        // Clear default items
        departmentBOX.removeAllItems();
        paylevelBOX.removeAllItems();

        // Get departments from the system
        ArrayList<Department> departments = system.listDepartments();
        System.out.println("Number of departments: " + departments.size());

        // First add a "Select Department" option
        departmentBOX.addItem("-- Select Department --");

        // Add departments to the combo box
        for (Department dept : departments) {
            System.out.println("Adding department: " + dept.getName());
            departmentBOX.addItem(dept.getName());
        }

        System.out.println("Items in department combo box: " + departmentBOX.getItemCount());

        updatePayLevels();
    }


    
    /**
     * Update pay levels based on selected department
     */
    private void updatePayLevels() {
        // Clear existing pay levels
        paylevelBOX.removeAllItems();
        
        // Add pay levels for the selected department
        for (String payLevel : system.getPayLevels()) {
            paylevelBOX.addItem(payLevel);
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

        lblTitle = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        firstNameInput = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lastNameInput = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        addressInput = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        departmentBOX = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        genderBOX = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtHireDate = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        paylevelBOX = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        poistionInput = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        btnSaveEmployee = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Add New Employee");

        lblDescription.setForeground(new java.awt.Color(102, 102, 102));
        lblDescription.setText("Create a new employee record in the system.  ");

        jLabel3.setText("First Name");

        firstNameInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameInputActionPerformed(evt);
            }
        });

        jLabel4.setText("Last Name");

        lastNameInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameInputActionPerformed(evt);
            }
        });

        jLabel5.setText("Position");

        addressInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressInputActionPerformed(evt);
            }
        });

        jLabel6.setText("Department");

        departmentBOX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        departmentBOX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                departmentBOXActionPerformed(evt);
            }
        });

        jLabel7.setText("Gender");

        genderBOX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jLabel8.setText("Hire Date");

        txtHireDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHireDateActionPerformed(evt);
            }
        });

        jLabel9.setText("Paylevel");

        paylevelBOX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Phone");

        poistionInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poistionInputActionPerformed(evt);
            }
        });

        jLabel11.setText("Address");

        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        btnSaveEmployee.setBackground(new java.awt.Color(67, 97, 238));
        btnSaveEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveEmployee.setText("Add Employee");
        btnSaveEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveEmployeeActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPhone)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(175, 175, 175))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(poistionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(departmentBOX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(lblDescription)
                            .addComponent(lblTitle)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(firstNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(lastNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(genderBOX, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(txtHireDate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(paylevelBOX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addressInput)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSaveEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDescription)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(firstNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(departmentBOX, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(poistionInput))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderBOX, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(txtHireDate, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paylevelBOX, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(addressInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void firstNameInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameInputActionPerformed

    private void lastNameInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameInputActionPerformed

    private void addressInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressInputActionPerformed

    private void txtHireDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHireDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHireDateActionPerformed

    private void poistionInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poistionInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_poistionInputActionPerformed

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void btnSaveEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveEmployeeActionPerformed
        try {
        // Get data from form fields
        String firstname = firstNameInput.getText().trim();
        String lastname = lastNameInput.getText().trim();
        String position = poistionInput.getText().trim();
        
        // Get selected department
        String departmentName = (String) departmentBOX.getSelectedItem();
        int departmentId = getDepartmentIdByName(departmentName);
        
        // Get gender from combo box
        String genderStr = (String) genderBOX.getSelectedItem();
        char gender = genderStr.charAt(0); // Take first character
        
        String hireDate = txtHireDate.getText().trim();
        
        // Get pay level from combo box
        String payLevelStr = (String) paylevelBOX.getSelectedItem();
        int payLevel = extractPayLevelNumber(payLevelStr);
        
        String phone = txtPhone.getText().trim();
        String address = addressInput.getText().trim();
        
        // Validate inputs
        if (firstname.isEmpty() || lastname.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both first and last name", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (isEditMode && employeeToEdit != null) {
            // Update existing employee
            employeeToEdit.setFirstName(firstname);
            employeeToEdit.setLastName(lastname);
            employeeToEdit.setGender(gender);
            employeeToEdit.setAddress(address);
            employeeToEdit.setPayLevel(payLevel);
            
            // Update department assignment if changed
            Department currentDept = employeeToEdit.getDepartment();
            int currentDeptId = (currentDept != null) ? currentDept.getDepartmentId() : -1;
            
            if (currentDeptId != departmentId) {
                // Department has changed, reassign employee
                system.assignEmployeeToDepartment(employeeToEdit.getEmployeeId(), departmentId);
            }
            
            JOptionPane.showMessageDialog(this, 
                "Employee updated successfully", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Create new employee
            system.addEmployee(firstname, lastname, gender, address, payLevel, phone, hireDate, position);
            
            // Get the newly created employee's ID
            int employeeId = getLastAddedEmployeeId();
            
            // Assign the employee to the department
            system.assignEmployeeToDepartment(employeeId, departmentId);
            
            JOptionPane.showMessageDialog(rootPane, 
                "Successfully added employee with id: " + employeeId, 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear the form
            clearForm();
        }
        
        // Close dialog if in edit mode, or if configured to close after adding
        if (isEditMode) {
            dispose();
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(rootPane, 
            "Error processing employee: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnSaveEmployeeActionPerformed

    private void departmentBOXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_departmentBOXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_departmentBOXActionPerformed
    
    
    
    // Helper method to get department ID by name
    private int getDepartmentIdByName(String departmentName) {
        // Skip if it's the default "Select Department" item
        if (departmentName == null || departmentName.contains("Select Department") || 
            departmentName.contains("No departments")) {
            throw new IllegalArgumentException("Please select a valid department");
        }

        ArrayList<Department> departments = system.listDepartments();
        for (Department dept : departments) {
            if (dept.getName().equals(departmentName)) {
                return dept.getDepartmentId();
            }
        }
        throw new IllegalArgumentException("Department not found: " + departmentName);
    }

    // Helper method to extract pay level number from formatted string
    private int extractPayLevelNumber(String payLevelStr) {
        // Example input: "Level 3 - $53,537.35"
        if (payLevelStr == null || !payLevelStr.startsWith("Level")) {
            throw new IllegalArgumentException("Invalid pay level format");
        }

        // Extract the number after "Level " and before " -"
        String levelPart = payLevelStr.substring(6, payLevelStr.indexOf(" -"));
        try {
            return Integer.parseInt(levelPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Could not parse pay level: " + payLevelStr);
        }
    }

    // Helper method to get the ID of the last added employee
    private int getLastAddedEmployeeId() {
        // This depends on how your system tracks employee IDs
        // If system.addEmployee() returns the ID, use that instead
        // This is just a placeholder - you need to implement this based on your system
        return system.getLastAddedEmployeeId(); // You'll need to add this method to HR_System
    }

    // Helper method to clear the form
    private void clearForm() {
        firstNameInput.setText("");
        lastNameInput.setText("");
        poistionInput.setText("");
        departmentBOX.setSelectedIndex(0);
        genderBOX.setSelectedIndex(0);
        txtHireDate.setText("");
        paylevelBOX.setSelectedIndex(0);
        txtPhone.setText("");
        addressInput.setText("");
        firstNameInput.requestFocus();
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddEmployeeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddEmployeeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddEmployeeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddEmployeeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddEmployeeDialog dialog = new AddEmployeeDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressInput;
    private javax.swing.JButton btnSaveEmployee;
    private javax.swing.JComboBox<String> departmentBOX;
    private javax.swing.JTextField firstNameInput;
    private javax.swing.JComboBox<String> genderBOX;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField lastNameInput;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JComboBox<String> paylevelBOX;
    private javax.swing.JTextField poistionInput;
    private javax.swing.JTextField txtHireDate;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
