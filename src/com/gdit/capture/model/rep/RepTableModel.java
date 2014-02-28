/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model.rep;

import com.gdit.capture.entity.Rep;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bahi
 */
public class RepTableModel extends DefaultTableModel {

    private List<Rep> reps = new ArrayList<Rep>();
    String columnNames[];
    private ResourceBundle bundle;
    JTable currentTable = null;

    public RepTableModel(JTable currentTable, List<Rep> computers, ResourceBundle bundle) {
        this.bundle = bundle;
        this.reps = computers;
        this.currentTable = currentTable;
        columnNames = new String[]{bundle.getString("rep.id"), bundle.getString("rep.name"), bundle.getString("edit"), bundle.getString("delete")};
    }

    @SuppressWarnings("finally")
    public List<Rep> getDataModel() {
        try {
            if (reps == null) {
                reps = new ArrayList<Rep>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return reps;
        }
    }

    public void addRow(Rep computer) {
        try {
            reps.add(computer);
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeRow(int rowIndex) {
        try {
            reps.remove(rowIndex);
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        if (reps != null) {
            return reps.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object value = null;
        try {
            Rep currentBean = reps.get(rowIndex);
            if (columnIndex == 0) {
                value = currentBean.getId();
            } else if (columnIndex == 1) {
                value = currentBean.getName();
            } else if (columnIndex == 2) {
                value = bundle.getString("edit");
            } else if (columnIndex == 3) {
                value = bundle.getString("delete");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            Rep currentBean = reps.get(rowIndex);
            if (columnIndex == 0) {
                currentBean.setName(aValue.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   

    public List<Rep> getReps() {
        return reps;
    }

    public void setReps(List<Rep> reps) {
        this.reps = reps;
    }
}
