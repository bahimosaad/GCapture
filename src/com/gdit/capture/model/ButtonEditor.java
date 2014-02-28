/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

import com.gdit.capture.model.user.UsersTableModel;
import com.gdit.capture.model.role.RoleTableModel;
import com.gdit.capture.entity.Computers;
import com.gdit.capture.entity.ComputersHome;
import com.gdit.capture.entity.Groups;
import com.gdit.capture.entity.GroupsHome;
import com.gdit.capture.entity.Investigation;
import com.gdit.capture.entity.InvestigationHome;
import com.gdit.capture.entity.Modules;
import com.gdit.capture.entity.ModulesHome;
import com.gdit.capture.entity.Roles;
import com.gdit.capture.entity.RolesHome;
import com.gdit.capture.entity.Users;
import com.gdit.capture.entity.UsersHome;
import com.gdit.capture.gui.ComputerGroupsDlg;
import com.gdit.capture.gui.ComputersDlg;
import com.gdit.capture.gui.ComputersPanel;
import com.gdit.capture.gui.GroupRolesDlg;
import com.gdit.capture.gui.GroupsGUI;
import com.gdit.capture.gui.RoleModulesDlg;
import com.gdit.capture.gui.RolesFrame;
import com.gdit.capture.gui.RolesPanel;
import com.gdit.capture.gui.UserGroupsDlg;
import com.gdit.capture.gui.UsersFrame;
import com.gdit.capture.gui.UsersPanel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;

/**
 *
 * @author bahy
 */
public class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String roleName;
    private ResourceBundle bundle;
    private JTable table;
    private int rowIndex;
    private Object parent;

    public ButtonEditor(JCheckBox checkBox, ResourceBundle bundle) {
        super(checkBox);
        this.bundle = bundle;
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public ButtonEditor(Object parent, JCheckBox checkBox, ResourceBundle bundle) {
        super(checkBox);
        this.bundle = bundle;
        button = new JButton();
        button.setOpaque(true);
        this.parent = parent;
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });

    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.table = table;
        this.rowIndex = row;
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        roleName = (String) table.getModel().getValueAt(row, 0);
        //  System.out.println("******************   "+roleName);
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        try {
            Object tm = table.getModel();
            if (isPushed) {
                if (label.equals(bundle.getString("delete"))) {
                    if (tm instanceof GroupTableModel) {
                        deleteGroup();
                    }   else if (tm instanceof ModuleTableModel) {
                        deleteModule();
                    } else if (tm instanceof ComputersTableModel) {
                        deleteComputer();
                    } else if (tm instanceof InvestigationTableModel) {
                        deleteInvestigation();
                    }
                } else if (label.equals(bundle.getString("edit"))) {
                    if (tm instanceof GroupTableModel) {
                        // updateGroup();
                        GroupTableModel model = (GroupTableModel) table.getModel();
                        Groups group = model.getGroups().get(rowIndex);
                        GroupRolesDlg grDlg = new GroupRolesDlg(group, ((GroupsGUI) parent), true);
                        grDlg.setVisible(true);

                    }  else if (tm instanceof ComputersTableModel) {
                        ComputersTableModel model = ((ComputersTableModel) table.getModel());
                        Computers computer = model.getComputers().get(rowIndex);
                        ComputerGroupsDlg dlg = new ComputerGroupsDlg(computer, ((ComputersPanel) parent), true);
                        dlg.setVisible(true);
                    } else if (tm instanceof ModuleTableModel) {
                        updateModule();
                    } else if (tm instanceof InvestigationTableModel) {

                        updateInvestigation();
                    }
                }

            }
            isPushed = false;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            RolesHome.close();
        }
        return new String(label);
    }

    private void deleteRole() {
        try {
            RolesHome dao = new RolesHome();
            RoleTableModel model = ((RoleTableModel) table.getModel());
            Roles role = model.getRoles().get(rowIndex);
            if (role.getId() == 0) {
                role = dao.findByName(role.getRoleName());
            }
            dao.delete(role);
            dao.commit();
            ((RoleTableModel) table.getModel()).removeRow(rowIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            RolesHome.close();
        }
    }

    private void deleteInvestigation() {
        try {
            InvestigationHome dao = new InvestigationHome();
            InvestigationTableModel model = ((InvestigationTableModel) table.getModel());
            Investigation investigation = model.getInvestigations().get(rowIndex);
            if (investigation.getId() == 0) {
                // investigation = dao.findByName(investigation.getName());
            }
            dao.delete(investigation);
            dao.commit();
            ((InvestigationTableModel) table.getModel()).removeRow(rowIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            InvestigationHome.close();
        }
    }

    private void deleteModule() {
        ModulesHome dao = new ModulesHome();
        ModuleTableModel model = ((ModuleTableModel) table.getModel());
        Modules module = model.getModules().get(rowIndex);
//        if (module.getId() == 0) {
//            module = dao.findByName(module.getRoleName());
//        }
        dao.delete(module);
        dao.commit();
        ((ModuleTableModel) table.getModel()).removeRow(rowIndex);
    }

    private void deleteGroup() {
        try {
            GroupsHome dao = new GroupsHome();
            GroupTableModel model = ((GroupTableModel) table.getModel());
            Groups group = model.getGroups().get(rowIndex);
            if (group.getId() == 0) {
                group = dao.findByName(group.getGroupName());
            }
            dao.delete(group);
            dao.commit();
            ((GroupTableModel) table.getModel()).removeRow(rowIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            GroupsHome.close();
        }
    }

    private void deleteUser() {
        try {
            UsersHome dao = new UsersHome();
            UsersTableModel model = ((UsersTableModel) table.getModel());
            Users user = model.getUsers().get(rowIndex);
            if (user.getId() == 0) {
                //  user = dao.findByName(user.getUserName());
            }
            dao.delete(user);
            dao.commit();
            ((UsersTableModel) table.getModel()).removeRow(rowIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            UsersHome.close();
        }
    }

    private void deleteComputer() {
        try {
            ComputersHome dao = new ComputersHome();
            ComputersTableModel model = ((ComputersTableModel) table.getModel());
            Computers computer = model.getComputers().get(rowIndex);
//        if (computer.getId() == 0) {
//            computer = dao.findByName(computer.getRoleName());
//        }
            dao.delete(computer);
            dao.commit();
            ((ComputersTableModel) table.getModel()).removeRow(rowIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ComputersHome.close();
        }
    }

    private void updateRole() {
        try {
            RolesHome dao = new RolesHome();
            RoleTableModel model = ((RoleTableModel) table.getModel());
            Roles role = model.getRoles().get(rowIndex);
            if (role.getId() == 0) {
                JOptionPane.showMessageDialog(button, "Editting Failed");

            } else {
                dao.attachDirty(role);
                dao.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            RolesHome.close();
        }
    }

    private void updateModule() {
        try {
            ModulesHome dao = new ModulesHome();
            ModuleTableModel model = ((ModuleTableModel) table.getModel());
            Modules module = model.getModules().get(rowIndex);
            if (module.getId() == 0) {
                JOptionPane.showMessageDialog(button, "Editting Failed");

            } else {
                dao.attachDirty(module);
                dao.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ModulesHome.close();
        }
    }

    private void updateInvestigation() {
        try {
             InvestigationHome dao = new InvestigationHome();
            InvestigationTableModel model = ((InvestigationTableModel) table.getModel());
            Investigation investigation = model.getInvestigations().get(rowIndex);
            if (investigation.getId() == 0) {
                JOptionPane.showMessageDialog(button, "Editting Failed");

            } else {
                dao.attachDirty(investigation);
                dao.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            InvestigationHome.close();
        }
    }

    private void updateGroup() {

        GroupsHome dao = new GroupsHome();
        GroupTableModel model = ((GroupTableModel) table.getModel());
        Groups group = model.getGroups().get(rowIndex);
//        ((GroupsGUI)parent).showGroup(group);
//        if (group.getId() == 0) {
//            JOptionPane.showMessageDialog(button, "Editting Failed");
//
//        } else {
//            dao.attachDirty(group);
//            dao.commit();
//        }
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
