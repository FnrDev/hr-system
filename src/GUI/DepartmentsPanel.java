/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;
import Logic.Department;
import Logic.Employee;
import Logic.HR_System;
import Logic.SystemManager;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.SwingUtilities;
import java.awt.Frame;
/**
 *
 * @author pvppl
 */
public class DepartmentsPanel extends javax.swing.JPanel {
    private HR_System system;
    private JTable departmentTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;
    private AddNewDepartmentDialog dialog;

    private Department department;
    private DepartmentsPanel parentPanel;
    private JTable employeeTable;
    private JScrollPane scrollPane;
    
    /**
     * Creates new form DepartmentsPanel
     */
    public DepartmentsPanel() {
        this.system = SystemManager.getInstance().getSystem();
    
        // Debug: Check if system is properly initialized
        if (system == null) {
            System.err.println("ERROR: HR_System is null!");
        } else {
            System.out.println("HR_System initialized successfully");
        }

        // Initialize components first
        initComponents();

        // Initialize the table and model
        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Name", "Location", "Budget", "Department Head", "Employees", "Actions"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only make the Actions column editable
            }
        };

        departmentTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(departmentTable);

        // Initialize the dialog
        dialog = new AddNewDepartmentDialog(null, true);
        dialog.setParentPanel(this);

        // Remove the example panel
        this.remove(jPanel3);

        // Use a simple BorderLayout
        this.setLayout(new BorderLayout());

        // Create a header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(employeeTitle1, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addDepartmentBTN);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Add components to this panel
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(tableScrollPane, BorderLayout.CENTER);

        // Set up the table
        setupDepartmentTable();

        // Load departments
        loadDepartments();

        // Force the UI to update
        this.revalidate();
        this.repaint();
    }
    
    // New methods for table functionality
    private void setupDepartmentTable() {
        // Make sure the table is visible
        departmentTable.setVisible(true);
        tableScrollPane.setVisible(true);

        // Set preferred size for the table scroll pane
        tableScrollPane.setPreferredSize(new Dimension(1100, 400));

        // Set custom renderer for the Actions column
        departmentTable.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof JPanel) {
                    return (JPanel) value;
                }
                // Return a default component if value is not a JPanel
                JPanel emptyPanel = new JPanel();
                return emptyPanel;
            }
        });

        // Set custom editor for the Actions column
        departmentTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, 
                    boolean isSelected, int row, int column) {
                if (value instanceof JPanel) {
                    return (JPanel) value;
                }
                // Return a default component if value is not a JPanel
                JPanel emptyPanel = new JPanel();
                return emptyPanel;
            }
        });

        // Set column widths
        departmentTable.getColumnModel().getColumn(0).setPreferredWidth(200); // Name
        departmentTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Location
        departmentTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Budget
        departmentTable.getColumnModel().getColumn(3).setPreferredWidth(200); // Department Head
        departmentTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Employees
        departmentTable.getColumnModel().getColumn(5).setPreferredWidth(200); // Actions

        // Prevent column resizing by the user to maintain our custom widths
        departmentTable.getTableHeader().setResizingAllowed(false);

        // Set row height to accommodate buttons
        departmentTable.setRowHeight(40);

        // Add mouse listener to handle button clicks
        departmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = departmentTable.rowAtPoint(evt.getPoint());
                int col = departmentTable.columnAtPoint(evt.getPoint());

                if (col == 5 && row >= 0) { // Actions column
                    // Get the component at that cell
                    TableCellRenderer renderer = departmentTable.getCellRenderer(row, col);
                    Component comp = departmentTable.prepareRenderer(renderer, row, col);

                    if (comp instanceof JPanel) {
                        JPanel panel = (JPanel) comp;
                        // Calculate relative click position within the cell
                        Rectangle cellRect = departmentTable.getCellRect(row, col, false);
                        int x = evt.getX() - cellRect.x;
                        int y = evt.getY() - cellRect.y;

                        // Forward the click to the panel
                        panel.dispatchEvent(new java.awt.event.MouseEvent(
                            panel, evt.getID(), evt.getWhen(), evt.getModifiers(),
                            x, y, evt.getClickCount(), evt.isPopupTrigger(), evt.getButton()
                        ));
                    }
                }
            }
        });

        // Force the table to repaint
        departmentTable.revalidate();
        departmentTable.repaint();
    }
    
    private void loadDepartments() {
        try {
            // Clear existing rows
            tableModel.setRowCount(0);

            // Get all departments from the system
            ArrayList<Department> departments = system.listDepartments();

            System.out.println("Loading departments. Count: " + departments.size());

            if (departments.isEmpty()) {
                System.out.println("No departments found in the system.");
                // Add a dummy row to show the table is working
//                tableModel.addRow(new Object[]{"No departments found", "", "", "", "", ""});
            } else {
                System.out.println("Departments found:");
                for (Department dept : departments) {
                    System.out.println(" - " + dept.getName() + " (ID: " + dept.getDepartmentId()+ ")");
                    addDepartmentToTable(dept);
                }
            }

            // Force UI refresh
            forceTableRefresh();
        } catch (Exception e) {
            System.err.println("Error loading departments: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void forceTableRefresh() {
        // Force the table to repaint
        tableModel.fireTableDataChanged();
        departmentTable.revalidate();
        departmentTable.repaint();

        // Force the container to repaint
        tableScrollPane.revalidate();
        tableScrollPane.repaint();
        this.revalidate();
        this.repaint();
    }
    
private void addDepartmentToTable(Department dept) {
    try {
        if (dept == null) {
            System.out.println("Warning: Attempted to add null department to table");
            return;
        }
        
        // Get department head name (if assigned)
        String headName = "Not Assigned";
        if (dept.getHeadOfDepartment() != null) {
            headName = dept.getHeadOfDepartment().getFirstName() + " " + dept.getHeadOfDepartment().getLastName();
        }

        // Format budget as currency
        String budgetStr = "BD" + String.format("%,.2f", dept.getBudget());

        // Get employee count - this will correctly show the number of employees
        int employeeCount = dept.getEmployees().size();

        // Create action buttons panel
        JPanel actionPanel = createActionButtons(dept);

        // Create row data
        Object[] row = {
            dept.getName(),
            dept.getLocation(),
            budgetStr,
            headName,
            String.valueOf(employeeCount), // Shows employee count
            actionPanel
        };

        // Add row to table
        tableModel.addRow(row);

        System.out.println("Added row to table: " + dept.getName() + " with " + employeeCount + " employees");
    } catch (Exception e) {
        System.err.println("Error adding department to table: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    private JPanel createActionButtons(Department dept) {
        // Use GridLayout instead of FlowLayout for more consistent button sizing
        JPanel panel = new JPanel(new GridLayout(1, 3, 5, 0));

        // View Employees button
        JButton viewBtn = new JButton("View");
        viewBtn.setPreferredSize(new Dimension(70, 30));
        viewBtn.addActionListener(e -> viewDepartmentEmployees(dept));

        // Edit button
        JButton editBtn = new JButton("Edit");
        editBtn.setPreferredSize(new Dimension(70, 30));
        editBtn.addActionListener(e -> editDepartment(dept));

        // Delete button
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setPreferredSize(new Dimension(70, 30));
        deleteBtn.addActionListener(e -> deleteDepartment(dept));

        panel.add(viewBtn);
        panel.add(editBtn);
        panel.add(deleteBtn);

        // Make sure the panel has enough height
        panel.setPreferredSize(new Dimension(220, 35));

        return panel;
    }
    
    // Action methods for the buttons
    
private void viewDepartmentEmployees(Department dept) {
    try {
        DepartmentEmployeesDialog dialog = new DepartmentEmployeesDialog(
            javax.swing.SwingUtilities.getWindowAncestor(this) instanceof Frame ? 
            (Frame) javax.swing.SwingUtilities.getWindowAncestor(this) : null, 
            true
        );
        
        // Set department and parent panel
        dialog.setDepartment(dept);
        dialog.setParentPanel(this);
        
        // Show dialog
        dialog.setVisible(true);
    } catch (Exception e) {
        System.err.println("Error showing department employees: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(
            this, 
            "Error showing department employees: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

    private void editDepartment(Department dept) {
        // Configure the dialog for editing
        dialog.setDepartmentToEdit(dept);
        dialog.setParentPanel(this);
        dialog.setVisible(true);
    }
    
    private void deleteDepartment(Department dept) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete the department: " + dept.getName() + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            system.deleteDepartment(dept.getDepartmentId());
            JOptionPane.showMessageDialog(this, "Department deleted successfully");
            loadDepartments(); // Refresh the list
        }
    }
    
    public void refreshDepartments() {
        loadDepartments();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addDepartmentBTN = new javax.swing.JButton();
        employeeTitle1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnViewEmployees = new javax.swing.JButton();
        btnDeleteDepartment = new javax.swing.JButton();
        btnEditDepartment = new javax.swing.JButton();

        addDepartmentBTN.setBackground(new java.awt.Color(67, 97, 238));
        addDepartmentBTN.setForeground(new java.awt.Color(255, 255, 255));
        addDepartmentBTN.setText("Add Department");
        addDepartmentBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDepartmentBTNActionPerformed(evt);
            }
        });

        employeeTitle1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        employeeTitle1.setText("Department Managment");

        jPanel3.setBackground(new java.awt.Color(234, 234, 234));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(67, 97, 238));
        jLabel1.setText("Human Resources");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Location");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Budget");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Department Head");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Employees");

        jLabel6.setText("Floor 2, East Wing");

        jLabel7.setText("$250,000");

        jLabel8.setText("Jame Smith");

        jLabel9.setText("19");

        btnViewEmployees.setBackground(new java.awt.Color(67, 97, 238));
        btnViewEmployees.setForeground(new java.awt.Color(255, 255, 255));
        btnViewEmployees.setText("View Employees");

        btnDeleteDepartment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Trash-Linear-24px.png"))); // NOI18N
        btnDeleteDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteDepartmentActionPerformed(evt);
            }
        });

        btnEditDepartment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Edit2-Linear-24px (1).png"))); // NOI18N
        btnEditDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditDepartmentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(btnViewEmployees, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btnEditDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(67, 67, 67))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btnViewEmployees, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeleteDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(btnEditDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(880, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(employeeTitle1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addDepartmentBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeTitle1)
                    .addComponent(addDepartmentBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(157, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteDepartmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteDepartmentActionPerformed

    private void btnEditDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDepartmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditDepartmentActionPerformed

    private void addDepartmentBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDepartmentBTNActionPerformed
        dialog.resetForNewDepartment();
        dialog.setParentPanel(this);
        dialog.setVisible(true);
        refreshDepartments();
    }//GEN-LAST:event_addDepartmentBTNActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDepartmentBTN;
    private javax.swing.JButton btnDeleteDepartment;
    private javax.swing.JButton btnEditDepartment;
    private javax.swing.JButton btnViewEmployees;
    private javax.swing.JLabel employeeTitle1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
