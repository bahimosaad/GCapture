/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model.category;

import com.gdit.capture.entity.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Bahi
 */
public class CategoryTableModel extends DefaultTableModel {

    private List<Category> categories = new ArrayList<Category>();
    String columnNames[];
    private ResourceBundle bundle;
    JTable currentTable = null;

    public CategoryTableModel(JTable currentTable, List<Category> categories, ResourceBundle bundle) {
        this.bundle = bundle;
        this.categories = categories;
        this.currentTable = currentTable;
        columnNames = new String[]{bundle.getString("rep.id"), bundle.getString("category.name"), bundle.getString("edit"), bundle.getString("delete")};
    }

    @SuppressWarnings("finally")
    public List<Category> getDataModel() {
        try {
            if (categories == null) {
                categories = new ArrayList<Category>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return categories;
        }
    }

    public void addRow(Category category) {
        try {
            categories.add(category);
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeRow(int rowIndex) {
        try {
            categories.remove(rowIndex);
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        if (categories != null) {
            return categories.size();
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
            Category currentBean = categories.get(rowIndex);
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
            Category currentBean = categories.get(rowIndex);
            if (columnIndex == 0) {
                currentBean.setName(aValue.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
