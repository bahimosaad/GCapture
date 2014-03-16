/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.gui;

import com.jidesoft.swing.JideSwingUtilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

/* TopLevelDemo.java requires no other files. */
public class AdminFrame extends JFrame {

    private JMenuItem mnuStatistics;
    private JMenuItem mnuDataEntryUsers;
    private JMenuItem mnuScanUsers;
    private JMenuItem mnuQAUsers;
    private JMenuItem mnuExpUsers;
    private JMenuItem mnuIndexUsers;
    private JMenuItem mnuBatchData;

    public AdminFrame(Locale locale, ResourceBundle bundle) {
        this.bundle = bundle;
        this.locale = locale;
        createAndShowGUI();
        changeScreenDirection(this, locale);
        centerScreen();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        //  JFrame frame = new JFrame("TopLevelDemo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the menu bar.  Make it have a green background.
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(200, 20));

        // jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuFileExit = new javax.swing.JMenuItem();
        mnuManage = new javax.swing.JMenu();
        mnuManageUsers = new javax.swing.JMenuItem();
//        mnuManageModules = new JMenuItem();
        mnuManageComputers = new JMenuItem();

        mnuStatistics = new JMenu();
        mnuStatistics.setText("Statistics");

        mnuFile.setText("File");
        mnuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        bundle = java.util.ResourceBundle.getBundle("com/gdit/bundle/capture"); // NOI18N
        mnuFileExit.setText(bundle.getString("exit")); // NOI18N
//        mnuManageModules.setText(bundle.getString("modules"));
//        mnuManageModules.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                mnuModulesAction(e);
//            }
//        });

        mnuFileSignout = new JMenuItem(bundle.getString("logout"));
        mnuFileSignout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mnuSignoutAction(e);
            }
        });
        mnuFile.add(mnuFileSignout);
        mnuManageComputers.setText(bundle.getString("computers"));
        mnuManageComputers.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mnuComputersAction(e);
            }
        });
        mnuFileExit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitAction(evt);
            }
        });
        mnuFile.add(mnuFileExit);
        greenMenuBar.add(mnuFile);
        mnuManage.setText(bundle.getString("manage")); // NOI18N
        mnuManageUsers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        mnuManageUsers.setText(bundle.getString("users")); // NOI18N
        mnuManageUsers.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUsersAction(evt);
            }
        });
        mnuManage.add(mnuManageUsers);
        mnuManageRoles = new JMenuItem();
        mnuManageRoles.setText(bundle.getString("roles"));
        mnuManageRoles.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mnuRolesAction(e);
            }
        });

        mnuManageGroups = new JMenuItem();
        mnuManageGroups.setText(bundle.getString("groups"));
        mnuManageGroups.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mnuGroupsAction(e);
            }
        });
        mnuManageReps = new JMenuItem();
        mnuManageReps.setText(bundle.getString("reps"));
        mnuManageReps.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mnuRepsAction(e);
            }
        });

        mnuManageCategories = new JMenuItem();
        mnuManageCategories.setText(bundle.getString("categories"));
        mnuManageCategories.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mnuCategoriesAction(e);
            }
        });


        mnuManage.add(mnuManageGroups);
        mnuManage.add(mnuManageRoles);
//        mnuManage.add(mnuManageModules);
        mnuManage.add(mnuManageComputers);
        mnuManage.add(mnuManageReps);
        mnuManage.add(mnuManageCategories);
        greenMenuBar.add(mnuManage);
        ////////////  Setting Menue
        JMenu mnuSettings = new JMenu(bundle.getString("settings"));
        JMenuItem mnuScannerSetting = new JMenuItem(bundle.getString("scanner.setting"));
        mnuScannerSetting.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mnuScannerSettingAction(e);
            }
        });
        mnuSettings.add(mnuScannerSetting);

        JMenuItem mnuSystemSettings = new JMenuItem(bundle.getString("system.setting"));
        mnuSystemSettings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mnuSystemSettingsAction(e);
            }
        });
        mnuSettings.add(mnuSystemSettings);

        mnuDataEntryUsers = new JMenuItem();
        mnuDataEntryUsers.setText("Data Entry Users");
        mnuDataEntryUsers.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DataUsersProduct panel = new DataUsersProduct(bundle, locale);
                getContentPane().removeAll();
                getContentPane().add(panel);
                validate();
            }
        });

        mnuScanUsers = new JMenuItem();
        mnuScanUsers.setText("Scan Users Productivity");
        mnuScanUsers.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ScanProductivityPanel panel = new ScanProductivityPanel(bundle, locale);
                getContentPane().removeAll();
                getContentPane().add(panel);
                validate();
            }
        });

        mnuQAUsers = new JMenuItem();
        mnuQAUsers.setText("QA Users Productivity");
        mnuQAUsers.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                QAProductivityPanel panel = new QAProductivityPanel(bundle, locale, 3);
                getContentPane().removeAll(); 
                getContentPane().add(panel);
                validate();
            }
        });

        mnuExpUsers = new JMenuItem();
        mnuExpUsers.setText("Exception Users Productivity");
        mnuExpUsers.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                QAProductivityPanel panel = new QAProductivityPanel(bundle, locale, 4);
                getContentPane().removeAll();
                getContentPane().add(panel);
                validate();
            }
        });
        
        mnuIndexUsers = new JMenuItem();
        mnuIndexUsers.setText("Index Users Productivity");
        mnuIndexUsers.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                QAProductivityPanel panel = new QAProductivityPanel(bundle, locale, 5);
                getContentPane().removeAll();
                getContentPane().add(panel);
                validate();
            }
        });

        mnuBatchData = new JMenuItem();
        mnuBatchData.setText("Search Batches");
        mnuBatchData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BatchPanel panel = new BatchPanel();
                getContentPane().removeAll();
                getContentPane().add(panel);
                validate();
            }
        });



        mnuStatistics.add(mnuDataEntryUsers);
        mnuStatistics.add(mnuScanUsers);
        mnuStatistics.add(mnuQAUsers);
        mnuStatistics.add(mnuExpUsers);
        mnuStatistics.add(mnuIndexUsers);
        mnuStatistics.add(mnuBatchData);
        greenMenuBar.add(mnuStatistics);
        greenMenuBar.add(mnuSettings);
        setJMenuBar(greenMenuBar);
        //Display the window.
        pack();
        setSize(800, 600);
        setVisible(true);
    }

    private void mnuRepsAction(ActionEvent evt) {
        RepPanel catPanel = new RepPanel(locale, bundle);
        catPanel.setFrame(null);
        getContentPane().removeAll();
        getContentPane().add(catPanel);
        validate();
    }

    public void mnuSignoutAction(ActionEvent evt) {
        this.dispose();
        frame.setVisible(true);
    }

    public void centerScreen() {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height / 2;
        int screenWidth = screenSize.width / 2;
        setLocation(screenWidth - this.getWidth() / 2, screenHeight - this.getHeight() / 2);

    }

    private void mnuCategoriesAction(ActionEvent evt) {
        CategoryPanel catPanel = new CategoryPanel(locale, bundle);
        catPanel.setFrame(null);
        getContentPane().removeAll();
        getContentPane().add(catPanel);
        validate();
    }

    private void mnuExitAction(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        try {
            int result = JOptionPane.showConfirmDialog(this, bundle.getString("close_msg"));
            if (result == JOptionPane.OK_OPTION) {
                this.dispose();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // log.log(Level.SEVERE, "", ex);
        }
    }

    private void mnuScannerSettingAction(ActionEvent evt) {
        ScannerSettingPanel scSettPanel = new ScannerSettingPanel();
        scSettPanel.setFrame(null);
        getContentPane().removeAll();
        getContentPane().add(scSettPanel);
        validate();
    }

    private void mnuSystemSettingsAction(ActionEvent evt) {
        SystemSetting scSettPanel = new SystemSetting();
        scSettPanel.setFrame(null);
        getContentPane().removeAll();
        getContentPane().add(scSettPanel);
        validate();
    }

    private void mnuUsersAction(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        UsersPanel usersPanel = new UsersPanel();
        usersPanel.setFrame(this);
        getContentPane().removeAll();
        getContentPane().add(usersPanel, BorderLayout.CENTER);
        validate();
        // this.getContentPane().add(n)
    }

    private void mnuGroupsAction(java.awt.event.ActionEvent evt) {
        GroupsGUI panel = new GroupsGUI();
        panel.setFrame(this);
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        validate();
    }

    private void mnuRolesAction(java.awt.event.ActionEvent evt) {
        RolesPanel panel = new RolesPanel();
        panel.setFrame(this);
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        validate();
    }

    private void mnuModulesAction(java.awt.event.ActionEvent evt) {
        ModulesPanel panel = new ModulesPanel();
        panel.setFrame(this);
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        validate();
    }

    private void mnuComputersAction(ActionEvent evt) {
        ComputersPanel panel = new ComputersPanel();
        panel.setFrame(this);
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        validate();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                // AdminFrame demo = new AdminFrame();
//                demo.createAndShowGUI();
//                demo.centerScreen();
            }
        });
    }

    public static void changeScreenDirection(Component screen, Locale locale) {
        try {
            if (locale == null) {
                locale = new Locale("ar");
            }
            if (ComponentOrientation.getOrientation(locale).equals(ComponentOrientation.RIGHT_TO_LEFT)) {
                JideSwingUtilities.toggleRTLnLTR(screen);
                JideSwingUtilities.invalidateRecursively(screen);
                SwingUtilities.updateComponentTreeUI(screen);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    private ResourceBundle bundle;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuManage;
    //private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem mnuFileExit;
    private javax.swing.JMenuItem mnuManageUsers;
    private JMenuItem mnuManageRoles;
    private JMenuItem mnuManageGroups;
    private JMenuItem mnuManageComputers;
    // private JMenuItem mnuManageModules;
    private JMenuItem mnuManageReps;
    private JMenuItem mnuManageCategories;
    private JMenuItem mnuFileSignout;
    private Locale locale;
    private JFrame frame;
}
