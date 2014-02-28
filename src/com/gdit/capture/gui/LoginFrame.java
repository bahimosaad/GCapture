/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LoginFrame.java
 *
 * Created on 27/10/2010, 03:15:47 م
 */
package com.gdit.capture.gui;

import com.gdit.capture.entity.*;
import com.gdit.capture.ocr.OCRFrame;
import com.gdit.capture.run.*;
//import com.gdit.capture.service.SyncFiles;
//import com.gdit.capture.service.SyncFilesService;
import com.indexing.admin.view.AdminFrame;
import com.indexing.exp.view.IndexingExceptionFrame02;
import com.indexing.exp.view.IndexingExpFrame;
import com.indexing.qa.view.IndexingQAFrame;
import com.indexing.qa.view.IndexingQAFrame02;
import com.indexing.user.view.IndexingUserFrame;
import com.indexing.user.view.IndexingUserFrame02;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author hp
 */
public class LoginFrame extends javax.swing.JFrame {

    /** Creates new form LoginFrame */
    private static final Logger log = Logger.getAnonymousLogger();
    private String module;
    private Computers computer;
    private String computername;
    private Category category;
    public LoginFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
         if (locale == null) {
        locale = new Locale("ar");

        }
        bundle = ResourceBundle.getBundle("com/gdit/bundle/capture", locale);
        initComponents();
        init();
        changeScreenDirection(this, locale);
    }

    public boolean init() {
        try {
            cboLanguages.setModel(new javax.swing.DefaultComboBoxModel(new String[]{bundle.getString("arabic"), bundle.getString("english")}));
            ModulesHome dao = new ModulesHome();
            List<Modules> modules = dao.getAllModules();
            modulesModel = new DefaultComboBoxModel(modules.toArray());

//            for (Modules module : modules) {
//                modulesModel.addElement(module.getName());
//            }
            //      cboModules.setModel(modulesModel);
//            SyncFilesService service = new SyncFilesService();
//            SyncFiles port = service.getSyncFilesPort();
//            port.sendSystemOptions();
            // TODO add your handling code here:
            RepHome repHome = new RepHome();
             computername = InetAddress.getLocalHost().getHostName();
            ComputersHome compDao = new ComputersHome();
             computer = compDao.findByName(computername.toUpperCase());
            if ( computer == null) {
                JOptionPane.showMessageDialog(this, bundle.getString("error.computer.not.found"));
                return false;
            } else {
//                GeneralHome generalDao = new GeneralHome();
//                List<Roles> computerRoles = generalDao.getComputersRoles(computer);
                List<Rep> computerReps = repHome.getAllRep();
//                for (Roles role : computerRoles) {
//                    computerReps.addAll(generalDao.getRoleReps(role));
//                }
                repsModel = new DefaultComboBoxModel(computerReps.toArray());
                repsCombo.setModel(repsModel);
                Rep rep = (Rep) repsCombo.getSelectedItem();
//                Set<Category> categories = (Set<Category>) rep.getCategories();
//                categoriesModel = new DefaultComboBoxModel(categories.toArray());
//                categoriesCombo.setModel(categoriesModel);
            }
            txtUserName.addKeyListener(new KeyAdapter() {

                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_ENTER) {
                        Toolkit.getDefaultToolkit().beep();
                        txtPassword.requestFocus();
                    }
                }
            });
            txtPassword.addKeyListener(new KeyAdapter() {

                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_ENTER) {
                        Toolkit.getDefaultToolkit().beep();
                        loginBtn.requestFocus();
                    }
                }
            });
            loginBtn.addKeyListener(new KeyAdapter() {

                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_ENTER) {
                        Toolkit.getDefaultToolkit().beep();
                        //login();
                    }
                }
            });
            return true;
        } catch (org.hibernate.exception.GenericJDBCException ex) {
            JOptionPane.showMessageDialog(this, bundle.getString("database.connection.error"));
            ex.printStackTrace();
            return false;
        } catch (UnknownHostException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, bundle.getString("server.not.run"));
            return false;

        }
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
            log.log(Level.SEVERE, "", ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify thi s code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        cboLanguages = new javax.swing.JComboBox();
        loginBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        repsCombo = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                windowClosedAction(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                windowClosingAction(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loginKeyPressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/security1.jpg"))); // NOI18N

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/gdit/bundle/capture"); // NOI18N
        jLabel2.setText(bundle.getString("username")); // NOI18N

        txtUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserNameActionPerformed(evt);
            }
        });

        jLabel3.setText(bundle.getString("password")); // NOI18N

        jLabel4.setText(bundle.getString("language")); // NOI18N

        cboLanguages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLanguagesActionPerformed(evt);
            }
        });

        loginBtn.setText(bundle.getString("login")); // NOI18N
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        cancelBtn.setText(bundle.getString("cancel")); // NOI18N
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        jLabel6.setText(bundle.getString("reps")); // NOI18N

        repsCombo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        repsCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                repsComboItemChange(evt);
            }
        });
        repsCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repsComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(loginBtn)
                        .addGap(29, 29, 29)
                        .addComponent(cancelBtn)
                        .addGap(120, 120, 120))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(repsCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 306, Short.MAX_VALUE)
                            .addComponent(cboLanguages, javax.swing.GroupLayout.Alignment.TRAILING, 0, 306, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(repsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboLanguages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginBtn)
                    .addComponent(cancelBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserNameActionPerformed
    }//GEN-LAST:event_txtUserNameActionPerformed

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        // TODO add your handling code here:
        login();
    }//GEN-LAST:event_loginBtnActionPerformed

    public void login() {
        try {
            String userName = txtUserName.getText();
            String password = txtPassword.getText();
            UsersHome usersDao = new UsersHome();
            GeneralHome dao = new GeneralHome();
            Users user = usersDao.authnticateUser(userName, password);

            if (cboLanguages.getSelectedItem().equals(bundle.getString("arabic"))) {
                locale = new Locale("ar");
            } else {
                locale = new Locale("en");
            }
            //code only for KKUH
            //to get the Batches not Ocred
            //It should be commented after finish Vascular Lab  project
//            if (module.equals("unocred")) {
//                this.dispose();
//                Rep rep = (Rep) repsModel.getSelectedItem();
//                UnOcredBatchMain main = new UnOcredBatchMain(bundle, locale, user, rep);
//                main.setDefaultCloseOperation(CaptureMain.DISPOSE_ON_CLOSE);
//                //  main.setSize(800, 600);
//                main.centerScreen();
//                main.setVisible(true);
//            } 
            if (user != null) {
                  boolean authnticated = dao.authnticateUserModule(user, module);
              //  boolean authnticated = true;// dao.authnticateUserModule(user, module);
                if (authnticated) {
                    Rep rep = (Rep) repsModel.getSelectedItem();
                    List<Category> cats = dao.authnticateUserCategory(user, rep);
                    if(cats==null || cats.size() <=0){
                        JOptionPane.showMessageDialog(this, bundle.getString("cats.no.priv"));
                        return ;
                    }
                    else{
                        for(Category cat:cats){
                            if(cat.getDflt()!=null && cat.getDflt()){
                                category = cat;
                            }
                        }
                        if(category == null){
                            category = cats.get(0);
                        }
                    }
//                    Category category = (Category) categoriesModel.getSelectedItem();
//                    boolean catAuth = dao.authnticateUserCategory(user, category);
//                    if (!catAuth && !cboModules.getSelectedItem().toString().equalsIgnoreCase("Admin")) {
//                        JOptionPane.showMessageDialog(this, bundle.getString("category.not.auth"));
//                        return;
//                    }

                    
                        DiskHome diskHome = new DiskHome();
                         Disk disk = diskHome.getDefaultDisk(rep);
                         
                    if (module.endsWith("Scan")) {
                        this.dispose(); 
                        File scan = new File(disk.getPath());
                        File view = new File(disk.getViewPath());
                        File scanFile = null;
                        File viewFile = null;
                        if(category.isCreateFolder()){
                            scanFile = new File(disk.getPath()+"/"+category.getId()+"/scan");
                            viewFile = new File(disk.getPath()+"/"+category.getId()+"/view");
                        }
                        else{
                            scanFile = new File(disk.getPath()+"/scan");
                            viewFile = new File(disk.getPath()+"/view");
                        }
                        
                        
                        if(!scanFile.exists())
                            scanFile.mkdirs();
                        if(!viewFile.exists())
                            viewFile.mkdirs();
                        
                        CaptureMain main = new CaptureMain(bundle, locale, user, disk,computer,category,rep);
                        main.centerScreen();
                        main.setVisible(true);
                    } else if (module.equalsIgnoreCase("ScanQA")) {
                        this.dispose();
                        SwingUtilities.invokeLater(new QAMain(bundle, locale, user, rep));
//                       QAMain main = new QAMain(bundle, locale, user, rep);
//                       // QA main = new QA();
//                        main.setDefaultCloseOperation(CaptureMain.DISPOSE_ON_CLOSE);
//                          main.setSize(800, 600);
//                        main.centerScreen();
//                        main.setVisible(true);

                    } else if (module.equalsIgnoreCase("ScanException")) {
                        this.dispose();
                        ScanExceptionMain main = new ScanExceptionMain(bundle, locale, user, rep);
                        main.setDefaultCloseOperation(CaptureMain.DISPOSE_ON_CLOSE);
                        //  main.setSize(800, 600);
                        main.centerScreen();
                        main.setVisible(true);

                    }
                    else if (module.equalsIgnoreCase("Index")) {
                        this.dispose();
                        IndexingUserFrame02 main = new IndexingUserFrame02(category);
                        main.setDefaultCloseOperation(CaptureMain.DISPOSE_ON_CLOSE);
                        //  main.setSize(800, 600);
                          main.centerScreen();
                        main.setVisible(true);
                    } else if (module.equalsIgnoreCase("IndexQA")) {
                        this.dispose();
                        IndexingQAFrame02 main = new IndexingQAFrame02(category);
                        main.setDefaultCloseOperation(CaptureMain.DISPOSE_ON_CLOSE);
                        //  main.setSize(800, 600);
                          main.centerScreen();
                        main.setVisible(true);
                    } else if (module.equalsIgnoreCase("IndexException")) {
                        this.dispose();
                        IndexingExceptionFrame02 main = new IndexingExceptionFrame02(category);
                        main.setDefaultCloseOperation(CaptureMain.DISPOSE_ON_CLOSE);
                        //  main.setSize(800, 600);
                          main.centerScreen();
                        main.setVisible(true);
                    } else if (module.equalsIgnoreCase("IndexAdmin")) {
                        this.dispose();
                        AdminFrame main = new AdminFrame();
                        main.setDefaultCloseOperation(CaptureMain.DISPOSE_ON_CLOSE);
                        //  main.setSize(800, 600);
                          main.centerScreen();
                        main.setVisible(true);
                    } else if (module.equalsIgnoreCase("Admin")) {
                        this.dispose();
                        com.gdit.capture.gui.AdminFrame main = new com.gdit.capture.gui.AdminFrame(locale, bundle);
                        main.setDefaultCloseOperation(CaptureMain.DISPOSE_ON_CLOSE);
                        main.setFrame(this);
                        main.setSize(800, 600);
//                        main.createAndShowGUI();
//                        main.centerScreen();
                        main.setVisible(true);
                    } else if (module.equalsIgnoreCase("PatientDataEntry")) {
                        this.dispose();
                        JFrame frame = new JFrame();
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.add(new PatienDocPanel(user.getId()));
                        frame.setSize(500, 500);
                        frame.setVisible(true);
                    } else if (module.equalsIgnoreCase("OCR")) {
                        this.dispose();
                        OCRFrame frame = new OCRFrame();
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.add(new PatienDocPanel(user.getId()));
                        frame.setSize(750, 700);
                        frame.setVisible(true);
                    
                    } 
                    else if (module.equals("barcode")) {
                        this.dispose();
                       Barcode  barcode = new Barcode();
                     //  barcode.setSize(800, 800);
                       barcode.centerScreen();
                       barcode.setVisible(true);
                    }


                } else {
                    JOptionPane.showMessageDialog(this, bundle.getString("error.user.module.authntication"));
                }
            } else {
                JOptionPane.showMessageDialog(this, bundle.getString("error.user.login"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            clear();
        }
    }

    private void clear() {
        txtPassword.setText("");
      //  txtUserName.setText("");
    }

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
        try {
            int result = JOptionPane.showConfirmDialog(this, bundle.getString("close_msg"));
            if (result == JOptionPane.OK_OPTION) {
                this.dispose();
            }
        } catch (Exception ex) {
            log.log(Level.SEVERE, "", ex);
        }
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void cboLanguagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLanguagesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLanguagesActionPerformed

    private void windowClosedAction(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowClosedAction
        // TODO add your handling code here:
    }//GEN-LAST:event_windowClosedAction

    private void windowClosingAction(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowClosingAction
        // TODO add your handling code here:
    }//GEN-LAST:event_windowClosingAction

    private void loginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("Enetr");
        }
    }//GEN-LAST:event_loginKeyPressed

    private void repsComboItemChange(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_repsComboItemChange
        // TODO add your handling code here:
        try {
//            Rep rep = (Rep) repsCombo.getSelectedItem();
//            Set<Category> categories = (Set<Category>) rep.getCategories();
//            categoriesModel = new DefaultComboBoxModel(categories.toArray());
//            categoriesCombo.setModel(categoriesModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_repsComboItemChange

    private void repsComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repsComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_repsComboActionPerformed
    private String findComputerIP() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            //Get a string representation of the ip address
            String ipAddress = inetAddress.getHostAddress();
            //Print the ip address
            System.out.println(ipAddress);
            return ipAddress;
        } catch (Exception ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height / 2;
        int screenWidth = screenSize.width / 2;
        //setSize(screenWidth, screenHeight - 20);
        setLocation(screenWidth - this.getWidth() / 2, screenHeight - this.getHeight() / 2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        final String module;
        if (args == null || args.length <= 0) {
            module = "Index";
        } else {
            module = args[0];
        }
 
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                LoginFrame frame = new LoginFrame();
                if (frame.init()) {
                    frame.setModule(module);
                    frame.centerScreen();
                    frame.setVisible(true);
                }
            }
        });
    }
    ResourceBundle bundle;
    private static int tryLogin = 0;
    private Locale locale;
    private DefaultComboBoxModel modulesModel;
    private DefaultComboBoxModel repsModel;
    private DefaultComboBoxModel categoriesModel;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JComboBox cboLanguages;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JButton loginBtn;
    private javax.swing.JComboBox repsCombo;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
