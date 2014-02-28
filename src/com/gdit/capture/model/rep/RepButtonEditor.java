/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model.rep;

/**
 *
 * @author Bahi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import com.gdit.capture.gui.RepPanel;
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
 * @author bahy
 */
public class RepButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String roleName;
    private ResourceBundle bundle;
    private JTable table;
    private int rowIndex;
    private RepPanel parent;

    public RepButtonEditor(JCheckBox checkBox, ResourceBundle bundle) {
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

    public RepButtonEditor(RepPanel parent, JCheckBox checkBox, ResourceBundle bundle) {
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
            RepTableModel model = (RepTableModel) table.getModel();
            if (isPushed) {
                if (label.equals(bundle.getString("delete"))) {
                    try {
                        RepHome dao = new RepHome();
                        Rep rep = model.getReps().get(rowIndex);
                        List<Category> categories = dao.getRepCategories(rep);
                        if (categories != null && categories.size() > 0) {
                            JOptionPane.showMessageDialog(null, bundle.getString("rep.not.delete"));
                        } else {
                            dao.delete(rep);
                            dao.commit();
                            ((RepTableModel) table.getModel()).removeRow(rowIndex);
                            parent.clear();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        RepHome.close();
                    }
                } else if (label.equals(bundle.getString("edit"))) {
                    Rep rep = model.getReps().get(rowIndex);
                    parent.edit(rep);
                }

            }
            isPushed = false;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            RepHome.close();
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
