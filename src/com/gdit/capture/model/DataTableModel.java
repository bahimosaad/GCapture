/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bahi
 */
public class DataTableModel extends DefaultTableModel {

    private List<Object[]> data = new ArrayList<Object[]>();
    String columnNames[];

    public DataTableModel(List<Object[]> data) {
        this.data = data;
        columnNames = new String[]{"User","Date","Batches","Pages"};
    }

    @SuppressWarnings("finally")
    public List<Object[]> getDataModel() {
        try {
            if (data == null) {
                data = new ArrayList<Object[]>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return data;
        }
    }

    public void addRow(Object[] row) {
        try {
            data.add(row);
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeRow(int rowIndex) {
        try {
            data.remove(rowIndex);
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        if (data != null) {
            return data.size();
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
            Object[] currentBean = data.get(rowIndex);
            if (columnIndex == 0) {
                value = currentBean[0];
            } else if (columnIndex == 1) {
                value = currentBean[1];
            } else if (columnIndex == 2) {
                value = currentBean[2];

            
            } else if (columnIndex == 3) {
                value = currentBean[3];

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            Object[] currentBean = data.get(rowIndex);
            if (columnIndex == 0) {
                currentBean[0] = value;
            } else if (columnIndex == 1) {
                currentBean[1] = value;
            } else if (columnIndex == 2) {
                currentBean[2] = value;
            
            } else if (columnIndex == 32) {
                currentBean[3] = value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }
}
