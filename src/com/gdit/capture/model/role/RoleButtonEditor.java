/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model.role;

import com.gdit.capture.entity.Roles;
import com.gdit.capture.entity.RolesCategories;
import com.gdit.capture.entity.RolesHome;
import com.gdit.capture.gui.RoleCategoriesDlg;
import com.gdit.capture.gui.RoleModulesDlg;
import com.gdit.capture.gui.RoleRepsDlg;
import com.gdit.capture.gui.RolesPanel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Bahi
 */
public class RoleButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String roleName;
    private ResourceBundle bundle;
    private JTable table;
    private int rowIndex;
    private RolesPanel parent;

    public RoleButtonEditor(JCheckBox checkBox, ResourceBundle bundle) {
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

    public RoleButtonEditor(RolesPanel parent, JCheckBox checkBox, ResourceBundle bundle) {
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
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
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

    private void showModules() {
        RoleTableModel model = (RoleTableModel) table.getModel();
        Roles role = model.getRoles().get(rowIndex);
        RoleModulesDlg grDlg = new RoleModulesDlg(role, ((RolesPanel) parent), true);
        grDlg.setVisible(true);
    }
    private void showReps() {
        RoleTableModel model = (RoleTableModel) table.getModel();
        Roles role = model.getRoles().get(rowIndex);
        RoleRepsDlg grDlg = new RoleRepsDlg(role, ((RolesPanel) parent), true);
        grDlg.setVisible(true);
    }
    private void showCategory() {
        RoleTableModel model = (RoleTableModel) table.getModel();
        Roles role = model.getRoles().get(rowIndex);
        RoleCategoriesDlg grDlg = new RoleCategoriesDlg(role, ((RolesPanel) parent), true);
        grDlg.setVisible(true);
    }

    public Object getCellEditorValue() {
        try {
            RoleTableModel model = (RoleTableModel) table.getModel();
            if (isPushed) {
                if (label.equals(bundle.getString("delete"))) {
                    deleteRole();
                } else if (label.equals(bundle.getString("modules"))) {
                    showModules();

                } else if (label.equals(bundle.getString("reps"))) {
                    showReps();

                } else if (label.equals(bundle.getString("categories"))) {
                    showCategory();
                }

            }
            isPushed = false;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
