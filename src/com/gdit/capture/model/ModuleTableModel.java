/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model;

import com.gdit.capture.entity.Modules;
import com.gdit.capture.entity.Roles;
import com.jidesoft.grid.DefaultTableModelWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bahy
 */
public class ModuleTableModel extends DefaultTableModel{

    private List<Modules> modules = new ArrayList<Modules>();
    String columnNames[] ;
    private ResourceBundle bundle ;
    JTable currentTable = null;

    public ModuleTableModel(JTable currentTable,List<Modules> modules,ResourceBundle bundle){
        this.bundle = bundle;
        this.modules = modules;
        this.currentTable = currentTable;
        columnNames =  new String[]{bundle.getString("module.name"),bundle.getString("edit"),bundle.getString("delete")};

    }

    @SuppressWarnings("finally")
	public List<Modules> getDataModel() {
		try {
			if (modules == null) {
				modules= new ArrayList<Modules>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return modules;
		}
	}

    public void addRow(Modules module) {
		try {
			modules.add(module);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

     @Override
    public void removeRow(int rowIndex) {
		try {
			modules.remove(rowIndex);
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
	public int getRowCount() {
        if(modules!=null)
		return modules.size();
        else
            return 0;
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
			Modules currentBean = modules.get(rowIndex);
			if (columnIndex == 0) {
				value = currentBean.getName();
			} else if (columnIndex == 1) {
				value = bundle.getString("edit");
			} else if (columnIndex == 2) {
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
			Modules currentBean = modules.get(rowIndex);
			if (columnIndex == 0) {
				currentBean.setName(aValue.toString());
 			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    public List<Modules> getModules() {
        return modules;
    }

    public void setModules(List<Modules> modules) {
        this.modules = modules;
    }

   
//    @Override
//	public boolean isCellEditable(int rowIndex, int columnIndex) {
//		boolean editable = false;
//		try {
//			if (columnIndex == 0) {
//				editable = true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			return editable;
//		}
//	}



}
