/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model.category;

import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
import com.gdit.capture.gui.CategoryPanel;
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
public class CategoryButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String roleName;
    private ResourceBundle bundle;
    private JTable table;
    private int rowIndex;
    private CategoryPanel parent;

    public CategoryButtonEditor(JCheckBox checkBox, ResourceBundle bundle) {
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

    public CategoryButtonEditor(CategoryPanel parent, JCheckBox checkBox, ResourceBundle bundle) {
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
            CategoryTableModel model = (CategoryTableModel) table.getModel();
            if (isPushed) {
                if (label.equals(bundle.getString("delete"))) {
                    try {
                        CategoryHome dao = new CategoryHome();
                        Category category = model.getCategories().get(rowIndex);

                            dao.delete(category);
                            dao.commit();
                            ((CategoryTableModel) table.getModel()).removeRow(rowIndex);
                            parent.clear();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        CategoryHome.close();
                    }
                } else if (label.equals(bundle.getString("edit"))) {
                    Category category = model.getCategories().get(rowIndex);
                    parent.edit(category);
                }

            }
            isPushed = false;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CategoryHome.close();
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

