package GUI;

import Logic.HR_System;
import Logic.SystemManager;
import javax.swing.*;
import java.awt.*;

public class MainLayoutFrame extends javax.swing.JFrame {
     private HR_System system;

    
    private JPanel contentPanel;
    private EmployeesPanel employeesPanel;
    private DepartmentsPanel departmentsPanel;
    private PayrollReportsPanel payrollReportsPanel;
    private SettingsPanel settingsPanel;
    
    public MainLayoutFrame() {
        try {
            this.system = HR_System.loadData();
            System.out.println("System loaded from file.");
        } catch (Exception e) {
            this.system = new HR_System();
            System.out.println("New system created.");
        }
        SystemManager.getInstance().setSystem(this.system);
        
        // load startup file to initalize pre-determined data
        try {
            system.initializeFromFile("startup.txt");
        } catch (Exception e) {
            System.out.println("Error loading startup data: " + e.getMessage());
        }
        
       initComponents();
        if (PanelContent != null) {
            contentPanel = PanelContent;
            contentPanel.setLayout(new BorderLayout());
            employeesPanel = new EmployeesPanel();
            departmentsPanel = new DepartmentsPanel();
            payrollReportsPanel = new PayrollReportsPanel();
            settingsPanel = new SettingsPanel();

            contentPanel.add(employeesPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        } else {
            System.out.println("Error: PanelContent is null. Check NetBeans GUI design.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        navbar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        navbarTitle = new javax.swing.JLabel();
        btnExitApp = new javax.swing.JButton();
        PanelContent = new javax.swing.JPanel();
        employeesTabBTN = new javax.swing.JButton();
        departmentsTabBTN = new javax.swing.JButton();
        payrollTabBTN = new javax.swing.JButton();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        navbar.setBackground(new java.awt.Color(255, 255, 255));
        navbar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(216, 216, 218)));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/SRCS.png"))); // NOI18N

        navbarTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        navbarTitle.setForeground(new java.awt.Color(67, 97, 238));
        navbarTitle.setText("HR Department System");

        btnExitApp.setText("Exit Application");
        btnExitApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitAppActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navbarLayout = new javax.swing.GroupLayout(navbar);
        navbar.setLayout(navbarLayout);
        navbarLayout.setHorizontalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navbarTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExitApp, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jLabel1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(navbarTitle)
                    .addComponent(btnExitApp, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9))
        );

        PanelContent.setBackground(new java.awt.Color(255, 255, 255));
        PanelContent.setLayout(new java.awt.BorderLayout());

        employeesTabBTN.setText("Employees");
        employeesTabBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeesTabBTNActionPerformed(evt);
            }
        });

        departmentsTabBTN.setText("Departments");
        departmentsTabBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                departmentsTabBTNActionPerformed(evt);
            }
        });

        payrollTabBTN.setText("Payroll Reports");
        payrollTabBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payrollTabBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(departmentsTabBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(payrollTabBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeesTabBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1200, Short.MAX_VALUE)
                .addComponent(PanelContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(employeesTabBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(departmentsTabBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(payrollTabBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(332, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void employeesTabBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeesTabBTNActionPerformed
       System.out.println("Employees button clicked");
        if (contentPanel != null) {
            contentPanel.removeAll();
            contentPanel.add(employeesPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }//GEN-LAST:event_employeesTabBTNActionPerformed

    private void departmentsTabBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_departmentsTabBTNActionPerformed
        System.out.println("Departments button clicked");
        departmentsPanel.loadDepartments();
        if (contentPanel != null) {
            contentPanel.removeAll();
            contentPanel.add(departmentsPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }//GEN-LAST:event_departmentsTabBTNActionPerformed

    private void payrollTabBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payrollTabBTNActionPerformed
        System.out.println("Payroll Reports button clicked");
        payrollReportsPanel.refreshDepartmentsComboBox();
        if (contentPanel != null) {
            contentPanel.removeAll();
            contentPanel.add(payrollReportsPanel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }//GEN-LAST:event_payrollTabBTNActionPerformed

    private void btnExitAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitAppActionPerformed
        // Show confirmation dialog
        int confirmed = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to exit the application?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        // If user confirms, exit the application
        if (confirmed == JOptionPane.YES_OPTION) {
            system.exitSystem();
        }
        // If user selects No, the dialog closes and nothing happens
    }//GEN-LAST:event_btnExitAppActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainLayoutFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainLayoutFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainLayoutFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainLayoutFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainLayoutFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContent;
    private javax.swing.JButton btnExitApp;
    private javax.swing.JButton departmentsTabBTN;
    private javax.swing.JButton employeesTabBTN;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel navbar;
    private javax.swing.JLabel navbarTitle;
    private javax.swing.JButton payrollTabBTN;
    // End of variables declaration//GEN-END:variables
}
