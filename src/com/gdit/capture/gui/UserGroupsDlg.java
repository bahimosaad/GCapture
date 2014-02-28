/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserGroupsDlg.java
 *
 * Created on 25/10/2010, 06:39:20 م
 */
package com.gdit.capture.gui;

import com.gdit.capture.gui.UsersPanel;
import com.gdit.capture.entity.Groups;
import com.gdit.capture.entity.GroupsHome;
import com.gdit.capture.entity.Roles;
import com.gdit.capture.entity.RolesGroups;
import com.gdit.capture.entity.RolesHome;
import com.gdit.capture.entity.Users;
import com.gdit.capture.entity.UsersGroups;
import com.gdit.capture.entity.UsersGroupsHome;
import com.gdit.capture.entity.UsersHome;
import com.gdit.capture.model.ButtonEditor;
import com.gdit.capture.model.ButtonRenderer;
import com.gdit.capture.model.GroupTableModel;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author bahy
 */
public class UserGroupsDlg extends javax.swing.JDialog {

    /** Creates new form GroupsGUI */
    public UserGroupsDlg(Users user, UsersPanel parent, boolean modal) {
        super(parent.getFrame(), modal);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (locale == null) {
            locale = new Locale("ar");
        }
         
        this.user = user;
        bundle = ResourceBundle.getBundle("com/gdit/bundle/capture", locale);
        initComponents();
        init();
        changeScreenDirection(this, locale);
        centerScreen();
    }

    private void init() {
        try {
            GroupsHome groupsDao = new GroupsHome();
            chkList = new ArrayList<JCheckBox>();
            groups = groupsDao.getAllGroups();
            for (Groups role : groups) {
                chkList.add(new JCheckBox(role.getGroupName()));
            }
            groupsChkList.setListData(chkList.toArray());
            showUser();
//            GroupsHome groupsDao = new GroupsHome();
//            groups = groupsDao.getAllGroups();
//            groupsModel = new GroupTableModel(grousTable, groups, bundle);
//            grousTable.setModel(groupsModel);
//            grousTable.getColumn(bundle.getString("edit")).setCellRenderer(new ButtonRenderer());
//            grousTable.getColumn(bundle.getString("delete")).setCellRenderer(new ButtonRenderer());
//            grousTable.getColumn(bundle.getString("edit")).setCellEditor(new ButtonEditor(this, new JCheckBox(), bundle));
//            grousTable.getColumn(bundle.getString("delete")).setCellEditor(new ButtonEditor(this, new JCheckBox(), bundle));
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
//            GroupsHome.close();
        }
    }

    public void showUser() {
        //this.currentGroup = groups;
        txtUserName.setText(user.getUserName());
        //addBtn.setText(bundle.getString("edit"));
        Set<UsersGroups> rgs = user.getUsersGroupses();
        clearAllChkBoxes();
         
        for (UsersGroups rg : rgs) {
            //Roles role = (Roles) rolesDao.findByName(rg.getRoles().getRoleName());
            JCheckBox ckBox = new JCheckBox(rg.getGroups().getGroupName());
            ckBox.setSelected(true);
            int index = groups.indexOf(rg.getGroups());
            ckBox.setSelected(true);
            chkList.set(index, ckBox);
            groupsChkList.setListData(chkList.toArray());
            pack();
        }
    }

    private void clearAllChkBoxes() {
        for (JCheckBox ckBox : chkList) {
            ckBox.setSelected(false);
        }
        groupsChkList.setListData(chkList.toArray());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        groupsChkList = new CheckBoxList();
        jLabel2 = new javax.swing.JLabel();
//        jScrollPane2 = new javax.swing.JScrollPane();
        // grousTable = new javax.swing.JTable();
        addBtn = new javax.swing.JButton();
        //   jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/gdit/bundle/capture"); // NOI18N
        jLabel1.setText(bundle.getString("username")); // NOI18N

        jScrollPane1.setViewportView(groupsChkList);

        jLabel2.setText(bundle.getString("groups")); // NOI18N


        //      jScrollPane2.setViewportView(grousTable);

        addBtn.setText(bundle.getString("edit")); // NOI18N

        //   jLabel3.setText(bundle.getString("groups"));

        addBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addBtnActionPerformed(e);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(txtUserName).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(addBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).addContainerGap(15, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(22, 22, 22).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(addBtn).addGap(13, 13, 13).addGap(29, 29, 29)));

     
          

             
        pack();
    }// </editor-fold>

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        try {
            user.setUserName(txtUserName.getText());
           UsersHome dao = new UsersHome();
            UsersGroupsHome ugHome = new UsersGroupsHome();
            Set usersGroups = new HashSet();         
            for (int i = 0; i < chkList.size(); i++) {
                JCheckBox chk = chkList.get(i);
                if (chk.isSelected()) {
                    UsersGroups rg = new UsersGroups();
                    rg.setUsers(user);
                    Groups group = groups.get(i);
                    rg.setGroups(group);
                    usersGroups.add(rg);
                }
                 
            }
            ugHome.deleteUserGroups(user);
          
//            user.setUsersGroupses(usersGroups);
            dao.merge(user);
            
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            UsersHome.close();
            UsersGroupsHome.close();
           // clear();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
//                UserGroupsDlg dialog = new UserGroupsDlg(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
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
            log.log(Level.SEVERE, "", ex);
        }
    }

     private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height / 2;
        int screenWidth = screenSize.width / 2;
        //setSize(screenWidth, screenHeight - 20);
        setLocation(screenWidth - this.getWidth() / 2, screenHeight - this.getHeight() / 2);
    }

    public void clear() {
        txtUserName.setText("");
        clearAllChkBoxes();
        
    }

   
    private List<JCheckBox> chkList;
    private List<Groups> groups;
    private Users user;
    private Groups currentGroup;
    //  private GroupTableModel groupsModel;
    private ResourceBundle bundle;
    private Locale locale;
    private static final Logger log = Logger.getAnonymousLogger();
    // Variables declaration - do not modify
    private javax.swing.JButton addBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
//    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    //private javax.swing.JScrollPane jScrollPane2;
    //   private javax.swing.JTable grousTable;
    private CheckBoxList groupsChkList;
    private javax.swing.JTextField txtUserName;
     
}