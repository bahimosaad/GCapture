/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model.user;

/**
 *
 * @author Bahi
 */
import com.gdit.capture.entity.Users;
import com.gdit.capture.entity.UsersHome;
import com.gdit.capture.gui.UserGroupsDlg;
import com.gdit.capture.gui.UsersPanel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author bahy
 */
public class UserButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String roleName;
    private ResourceBundle bundle;
    private JTable table;
    private int rowIndex;
    private UsersPanel parent;

    public UserButtonEditor(JCheckBox checkBox, ResourceBundle bundle) {
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

    public UserButtonEditor(UsersPanel parent, JCheckBox checkBox, ResourceBundle bundle) {
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

    public Object getCellEditorValue() {
        try {
            UsersTableModel model = (UsersTableModel) table.getModel();
            if (isPushed) {
                if (label.equals(bundle.getString("delete"))) {
                    deleteUser(model);
                } else if (label.equals(bundle.getString("edit"))) {
                    editUser(model);
                } else if (label.equals(bundle.getString("groups"))) {
                    showGroups(model);
                }
            }
            isPushed = false;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            UsersHome.close();
        }
        return new String(label);
    }

    private void showGroups(UsersTableModel model) {
        Users user = model.getUsers().get(rowIndex);
        UserGroupsDlg dlg = new UserGroupsDlg(user, ((UsersPanel) parent), true);
        dlg.setVisible(true);
    }

    private void deleteUser(UsersTableModel model) {
        try {
            UsersHome dao = new UsersHome();
            Users user = model.getUsers().get(rowIndex);
            // List<Capture> categories = dao.get;
            if (user.getCaptures() != null && user.getCaptures().size() > 0) {
                JOptionPane.showMessageDialog(null, bundle.getString("rep.not.delete"));
            } else {
                dao.delete(user);
                dao.commit();
                ((UsersTableModel) table.getModel()).removeRow(rowIndex);
                parent.clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            UsersHome.close();
        }
    }

    private void editUser(UsersTableModel model) {
        Users rep = model.getUsers().get(rowIndex);
        parent.edit(rep);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
